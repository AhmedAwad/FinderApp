package org.trendak.finderapp.faceintelligence.azure;

import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class checkFaceMatching {

	public static String runAPI(String id1, String id2) {
		
		HttpClient httpclient = HttpClients.createDefault();
		String jsonString = "";

			try {
				URIBuilder builder = new URIBuilder(
						"https://westcentralus.api.cognitive.microsoft.com/face/v1.0/verify");

				URI uri = builder.build();
				HttpPost request = new HttpPost(uri);
				request.setHeader("Content-Type", "application/json");
				request.setHeader("Ocp-Apim-Subscription-Key", "64b1d23d99ac47749b955b4c043056d1");

				String body = "{\"faceId1\":\"" + id1 + "\", \"faceId2\":\"" + id2 + "\"}";
				StringEntity reqEntity = new StringEntity(body);
				request.setEntity(reqEntity);

				HttpResponse response = httpclient.execute(request);
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					 jsonString = EntityUtils.toString(entity).trim();

				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		return jsonString; 

	}

}
