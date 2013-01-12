package com.ijg.darklight.sdk.web.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class APIRequest {

	private URI requestURI;
	private URL requestURL;
	private URLConnection connection;
	
	private JsonObject response;
	
	public APIRequest(String protocol, String server, String requestURL, String query) {
		try {
			requestURI = new URI(protocol, server, requestURL, query, null);
			this.requestURL = requestURI.toURL();
		} catch (URISyntaxException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
		try {
			connection = requestURL.openConnection();
			
			StringBuilder rawResponse = new StringBuilder();
			Scanner scanner = new Scanner(connection.getInputStream());
			
			while (scanner.hasNext()) {
				rawResponse.append(scanner.next());
			}
			scanner.close();
			JsonParser jsonParser = new JsonParser();
			response = jsonParser.parse(rawResponse.toString()).getAsJsonObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JsonObject getResponse() {
		return response;
	}
	
	public Object get(String query) {
		return response.get(query);
	}
}
