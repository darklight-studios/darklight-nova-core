package com.ijg.darklight.sdk.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Settings {
	private static File settingsFile = new File(new File("."), "config.json");
	private static HashMap<String, JsonObject> objectsToSerialize = new HashMap<String, JsonObject>();
	
	private String nameFile = "C:\\Darklight Core\\name.dat";
	private String sessionType = "individual";
	
	private boolean	apiEnabled = false;
	private int apiID = 0;
	private String apiProtocol = "http";
	private String apiServer = "";
	
	private boolean	verificationEnabled = false;
	private ArrayList<String> verificationNames = new ArrayList<String>();
	private ArrayList<String> verificationTeams = new ArrayList<String>();
	
	public static void setSettingsFile(File settingsFile) {
		Settings.settingsFile = settingsFile;
	}
	
	public static Settings createInstance() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser jsonParser = new JsonParser();
		JsonObject rawSettings = jsonParser.parse(new FileReader(Settings.settingsFile)).getAsJsonObject();
		Gson gson = new Gson();
		Settings settings = gson.fromJson(rawSettings, Settings.class);
		return settings;
	}
	
	public static JsonObject deseralizeObject(String object) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser jsonParser = new JsonParser();
		JsonObject rawSettings = jsonParser.parse(new FileReader(Settings.settingsFile)).getAsJsonObject();
		return rawSettings.get(object).getAsJsonObject();
	}
	
	public static void addObjectToSerialize(String name, JsonObject object) {
		objectsToSerialize.put(name, object);
	}
	
	public void serialize() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonToWrite = jsonParser.parse(gson.toJson(this)).getAsJsonObject();
		String json;
		
		Iterator<Entry<String, JsonObject>> iter = Settings.objectsToSerialize.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, JsonObject> entry = iter.next();
			jsonToWrite.add(entry.getKey(), entry.getValue());
		}
		json = gson.toJson(jsonToWrite);

		FileWriter fw = new FileWriter(Settings.settingsFile);
		fw.write(json);
		fw.flush();
		fw.close();
	}
	
	public String getNameFile() {
		return nameFile;
	}
	
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	
	public String getSessionType() {
		return sessionType;
	}
	
	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}
	
	public boolean isApiEnabled() {
		return apiEnabled;
	}
	
	public void setApiEnabled(boolean apiEnabled) {
		this.apiEnabled = apiEnabled;
	}
	
	public int getApiID() {
		return apiID;
	}
	
	public void setApiID(int apiID) {
		this.apiID = apiID;
	}
	
	public String getApiProtocol() {
		return apiProtocol;
	}
	
	public void setApiProtocol(String apiProtocol) {
		this.apiProtocol = apiProtocol;
	}
	
	public String getApiServer() {
		return apiServer;
	}
	
	public void setApiServer(String apiServer) {
		this.apiServer = apiServer;
	}
	
	public boolean isVerificationEnabled() {
		return verificationEnabled;
	}
	
	public void setVerificationEnabled(boolean verificationEnabled) {
		this.verificationEnabled = verificationEnabled;
	}
	
	public ArrayList<String> getVerificationNames() {
		return verificationNames;
	}
	
	public void setVerificationNames(ArrayList<String> verificationNames) {
		this.verificationNames = verificationNames;
	}
	
	public ArrayList<String> getVerificationTeams() {
		return verificationTeams;
	}
	
	public void setVerificationTeams(ArrayList<String> verificationTeams) {
		this.verificationTeams = verificationTeams;
	}
}
