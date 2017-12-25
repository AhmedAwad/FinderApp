package org.trendak.finderapp.textintelligence.extractors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GenderExtractor {

	public static String getGender(String name) {
		
		String gender = ""; 

		try {
			

			String myKey = "xUVVBCKArmGqvwGdEw";
		
			URL url = new URL("https://gender-api.com/get?key=" + myKey + "&name=" + name);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (conn.getResponseCode() != 200) {

				extracted(conn); 

			}

			InputStreamReader input = new InputStreamReader(conn.getInputStream());

			BufferedReader reader = new BufferedReader(input);

			Gson gson = new Gson();

			JsonObject json = gson.fromJson(reader, JsonObject.class);

			 gender = json.get("gender").getAsString();

 
			conn.disconnect();

		} catch (IOException e) { 

			e.printStackTrace();

		}
		
		return gender;

	}

	private static void extracted(HttpURLConnection conn) throws IOException {
		throw new RuntimeException("Error: " + conn.getResponseCode());
	}

}