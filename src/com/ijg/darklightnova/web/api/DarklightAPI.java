package com.ijg.darklightnova.web.api;

import java.util.HashMap;
import org.json.simple.JSONObject;

public class DarklightAPI {
	public static String AUTH_ENDPOINT = "/api/auth";
	public static String UPDATE_ENDPOINT = "/api/score";
	public static String BASE_URL = "/session/";
	
	String PROTOCOL;
	String SERVER;
	
	public DarklightAPI(String protocol, String server) {
		this.PROTOCOL = protocol;
		this.SERVER = server;
	}
	
	public APIRequest sessionAuthRequest(int sessionID, String name) {
		APIRequest authRequest = new APIRequest(PROTOCOL, SERVER, BASE_URL + sessionID + AUTH_ENDPOINT, "name=" + name);
		authRequest.send();
		return authRequest;
	}
	
	public APIRequest sessionUpdateRequest(int sessionID, String sessionKey, int score, HashMap<String, String> foundIssues) {
		String desc = JSONObject.toJSONString(foundIssues);
		APIRequest updateRequest = new APIRequest(PROTOCOL, SERVER, BASE_URL + sessionID + UPDATE_ENDPOINT, 
				"sessionkey=" + sessionKey + "&score=" + score + "&desc=" + desc);
		updateRequest.send();
		return updateRequest;
	}
}
