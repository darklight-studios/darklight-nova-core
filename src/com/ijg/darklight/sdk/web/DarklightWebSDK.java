package com.ijg.darklight.sdk.web;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ijg.darklight.sdk.web.api.APIEndpoint;
import com.ijg.darklight.sdk.web.api.APIRequest;
import com.ijg.darklight.sdk.web.api.DarklightWebAPI;

public class DarklightWebSDK {
	String APIProtocol;
	String APIServer;
	
	int APISessionId;
	String APISessionKey;
	
	private DarklightWebAPI webAPI;
	
	JsonObject lastRequestResponse;
	
	public DarklightWebSDK() {}
	
	public DarklightWebSDK(String APIProtocol, String APIServer, int APISessionId) {
		this.APIProtocol = APIProtocol;
		this.APIServer = APIServer;
		this.APISessionId = APISessionId;
		if (!createAPI()) {
			System.err.println("[DarklightWebSDK] error creating API");
		}
	}
	
	public String toJsonString(HashMap<?, ?> map) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(map);
	}
	
	public boolean auth(String name) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", name);
		
		APIRequest authRequest = webAPI().individualSessionRequest(APISessionId, 
				APIEndpoint.AUTH, parameters);
		authRequest.send();
		
		lastRequestResponse = authRequest.getResponse();
		
		int statusCode = (int) authRequest.get("statuscode");
		if (statusCode == 200 || statusCode == 201) {
			APISessionKey = (String) authRequest.get("sessionkey");
			return true;
		}
		System.err
				.println("[DarklightWebSDK] api auth error, returned with status code: "
						+ statusCode);
		
		try {
			System.err.println("[DarklightWebSDK] error description: "
					+ authRequest.get("desc"));
		} catch (Exception e) {}
		
		return false;
	}
	
	public boolean update(int score, HashMap<String, String> desc) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionkey", APISessionKey);
		parameters.put("score", "" + score);
		parameters.put("desc", toJsonString(desc));
		
		APIRequest updateRequest = webAPI().individualSessionRequest(APISessionId, 
				APIEndpoint.UPDATE, parameters);
		updateRequest.send();
		
		lastRequestResponse = updateRequest.getResponse();
		
		int statusCode = (int) updateRequest.get("statuscode");
		if (statusCode == 200 || statusCode == 201) {
			return true;
		}
		System.err
				.println("[DarklightWebSDK] api update error, returned with status code: "
						+ statusCode);
		try {
			System.err.println("[DarklightWebSDK] error description: "
					+ updateRequest.get("desc"));
		} catch (Exception e) {}
		
		return false;
	}
	
	private boolean APIReady() {
		if (APIProtocol != null && APIServer != null && APISessionId > 0) {
			return true;
		}
		return false;
	}
	
	public boolean createAPI() {
		if (APIReady()) {
			webAPI = new DarklightWebAPI(APIProtocol, APIServer);
			return true;
		}
		return false;
	}
	
	public DarklightWebAPI webAPI() {
		if (webAPI == null) {
			createAPI();
		}
		return webAPI;
	}

	public String getAPIProtocol() {
		return APIProtocol;
	}

	public void setAPIProtocol(String aPIProtocol) {
		APIProtocol = aPIProtocol;
	}

	public String getAPIServer() {
		return APIServer;
	}

	public void setAPIServer(String aPIServer) {
		APIServer = aPIServer;
	}

	public int getAPISessionId() {
		return APISessionId;
	}

	public void setAPISessionId(int aPISessionId) {
		APISessionId = aPISessionId;
	}
}
