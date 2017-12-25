// This sample uses the Apache HTTP client library(org.apache.httpcomponents:httpclient:4.2.4)
// and the org.json library (org.json:json:20170516).

package org.trendak.finderapp.faceintelligence.azure;


import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.trendak.finderapp.faceintelligence.IFaceIntelligence;



public class Azure implements IFaceIntelligence 
{
    // **********************************************
    // *** Update or verify the following values. *** 
    // **********************************************

    // Replace the subscriptionKey string value with your valid subscription key.
    public static  String subscriptionKey = "64b1d23d99ac47749b955b4c043056d1";

    // Replace or verify the region.
    //
    // You must use the same region in your REST API call as you used to obtain your subscription keys.
    // For example, if you obtained your subscription keys from the westus region, replace
    // "westcentralus" in the URI below with "westus".
    //
    // NOTE: Free trial subscription keys are generated in the westcentralus region, so if you are using
    // a free trial subscription key, you should not need to change this region.
    public static  String uriBase = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect";


    @Override
    public String runAPI(String imageLink)  
    {
    	 
    	String jsonString = ""; 
    	
        HttpClient httpclient = new DefaultHttpClient();

        try
        {
            URIBuilder builder = new URIBuilder(uriBase);

            // Request parameters. All of them are optional.
            builder.setParameter("returnFaceId", "true");
            builder.setParameter("returnFaceLandmarks", "false");
            builder.setParameter("returnFaceAttributes", "age,gender");

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
            
            
            String path = imageLink;
            
            
            // Request body.
            StringEntity reqEntity = new StringEntity("{\"url\":\""+path+"\"}");
            request.setEntity(reqEntity);
            

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
               jsonString = EntityUtils.toString(entity).trim();
               
            }
        } 
        catch (Exception e)
        {
            // Display error message.
            System.out.println(e.getMessage());
        }
        
        return jsonString;  
    }
}