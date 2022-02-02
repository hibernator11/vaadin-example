package org.vaadin.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class SearchService implements Serializable {

	private static final long serialVersionUID = 1L;

	private String lcService = "https://www.loc.gov/search/?fo=json&q=";
	
	public ArrayList<LCResult> search(String text) {
        return getData(lcService + text);
	}
	
	private ArrayList<LCResult> getData(String urlSearch) {
		ArrayList<LCResult> result = new ArrayList<LCResult>(); 
		String stringResult = "";
		try {
			// Connect to the URL using java's native library
			URL url = new URL(urlSearch);
			URLConnection request = url.openConnection();
			request.connect();

			BufferedReader reader = null;

			HttpsURLConnection httpsURLConnection = null;
			try {
				httpsURLConnection = (HttpsURLConnection) url.openConnection();

				// create the connection and open it
				reader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
				// start reading the response given by the HTTP response
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			StringBuilder jsonString = new StringBuilder();

			// using string builder as it is more efficient for append operations
			String line;
			// append the string response to our jsonString variable
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			
			// dont forget to close the reader
			reader.close();
			
			// close the http connection
			httpsURLConnection.disconnect();
			
			stringResult= jsonString.toString();
		} catch (IOException e1) {
			System.out.println("Malformed URL or issues with the reader.");
			e1.printStackTrace();
		}
		
		JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(stringResult);
        JsonArray jsonArray = jo.getAsJsonArray("results");
        
        for(JsonElement item : jsonArray){
            JsonObject itemObj = item.getAsJsonObject();
        	String url = itemObj.get("id").getAsString();
            String title = itemObj.get("title").getAsString();
        	result.add(new LCResult(url, title));
        }
		
		return result;
	}
}
