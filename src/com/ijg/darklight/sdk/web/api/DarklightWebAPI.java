package com.ijg.darklight.sdk.web.api;

import java.util.HashMap;
import java.util.Map.Entry;

public class DarklightWebAPI {
	String protocol;
	String server;
	
	/**
	 * 
	 * @param protocol Protocol used by the API server
	 * @param server Web address of the API server
	 */
	public DarklightWebAPI(String protocol, String server) {
		this.protocol = protocol;
		this.server = server;
	}
	
	/**
	 * Create an individual session request
	 * @param sessionID ID of API session
	 * @param endpoint Endpoint to which this request will be sent
	 * @param parameters Paremeters with wich this request will be formatted
	 * @return The generated APIRequest
	 */
	public APIRequest individualSessionRequest(int sessionID, APIEndpoint endpoint, HashMap<String, String> parameters) {
		String query = makeQueryString(parameters);
		APIRequest request = new APIRequest(protocol, server, APIEndpoint.INDIVIDUAL.endpoint() + sessionID + endpoint.endpoint(), query);
		return request;
	}
	
	/**
	 * Utility to create a query string based off of a HashMap of parameters
	 * @param parameters HashMap of query strings
	 * @return Formatted string suitable to be prepended to a URL
	 */
	private String makeQueryString(HashMap<String, String> parameters) {
		StringBuilder query = new StringBuilder();
		for (Entry<String, String> pair : parameters.entrySet()) {
			query.append(pair.getKey() + "=" + pair.getValue() + "&");
		}
		return query.toString();
	}
}
