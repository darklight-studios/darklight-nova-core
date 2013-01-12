package com.ijg.darklight.sdk.web.api;

import java.util.HashMap;
import java.util.Map.Entry;

public class DarklightWebAPI {
	String protocol;
	String server;
	
	public DarklightWebAPI(String protocol, String server) {
		this.protocol = protocol;
		this.server = server;
	}
	
	public APIRequest individualSessionRequest(int sessionID, APIEndpoint endpoint, HashMap<String, String> parameters) {
		String query = makeQueryString(parameters);
		APIRequest request = new APIRequest(protocol, server, APIEndpoint.INDIVIDUAL.endpoint() + sessionID + endpoint.endpoint(), query);
		return request;
	}
	
	private String makeQueryString(HashMap<String, String> parameters) {
		StringBuilder query = new StringBuilder();
		for (Entry<String, String> pair : parameters.entrySet()) {
			query.append(pair.getKey() + "=" + pair.getValue() + "&");
		}
		return query.toString();
	}
}
