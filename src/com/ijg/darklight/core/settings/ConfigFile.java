package com.ijg.darklight.core.settings;

import java.util.ArrayList;

public class ConfigFile {

	String progressFile = "";
	String sessionType = "";
	String apiServer = ""; 
	boolean enableApi = false;
	int apiIdentifier = 1;
	int apiProtocol = 9;
	
	boolean enableNameVerification = true;
	ArrayList<String> validTeamNames;
	ArrayList<String> validTeamMembers;
	
	public String getProgressFile() {
		return progressFile;
	}

	public void setProgressFile(String progressFile) {
		this.progressFile = progressFile;
	}

	public String getSessionType() {
		return sessionType;
	}

	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}

	public String getApiServer() {
		return apiServer;
	}

	public void setApiServer(String apiServer) {
		this.apiServer = apiServer;
	}

	public boolean isEnableApi() {
		return enableApi;
	}

	public void setEnableApi(boolean enableApi) {
		this.enableApi = enableApi;
	}

	public int getApiIdentifier() {
		return apiIdentifier;
	}

	public void setApiIdentifier(int apiIdentifier) {
		this.apiIdentifier = apiIdentifier;
	}

	public int getApiProtocol() {
		return apiProtocol;
	}

	public void setApiProtocol(int apiProtocol) {
		this.apiProtocol = apiProtocol;
	}

	public boolean isEnableNameVerification() {
		return enableNameVerification;
	}

	public void setEnableNameVerification(boolean enableNameVerification) {
		this.enableNameVerification = enableNameVerification;
	}

	public ArrayList<String> getValidTeamNames() {
		return validTeamNames;
	}

	public void setValidTeamNames(ArrayList<String> validTeamNames) {
		this.validTeamNames = validTeamNames;
	}

	public ArrayList<String> getValidTeamMembers() {
		return validTeamMembers;
	}

	public void setValidTeamMembers(ArrayList<String> validTeamMembers) {
		this.validTeamMembers = validTeamMembers;
	}

	public ConfigFile() {
		
	}

}
