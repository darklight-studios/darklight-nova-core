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

/*
 * Copyright (C) 2013  Isaac Grant
 * 
 * This file is part of the Darklight Nova Core.
 *  
 * Darklight Nova Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Darklight Nova Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Darklight Nova Core.  If not, see <http://www.gnu.org/licenses/>.
 */

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
	
	/**
	 * Set config.json file this class uses to both read settings from, and write new settings to
	 * @param settingsFile The config.json file to use
	 */
	public static void setSettingsFile(File settingsFile) {
		Settings.settingsFile = settingsFile;
	}
	
	/**
	 * Create an instance of this class with the settings read from the settings file
	 * @see #setSettingsFile(File)
	 * @return An instance of this class with the settings read from the settings file
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 * @throws FileNotFoundException
	 */
	public static Settings createInstance() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser jsonParser = new JsonParser();
		JsonObject rawSettings = jsonParser.parse(new FileReader(Settings.settingsFile)).getAsJsonObject();
		Gson gson = new Gson();
		Settings settings = gson.fromJson(rawSettings, Settings.class);
		return settings;
	}
	
	/**
	 * Deserialize an object from the settings file into a JsonObject
	 * @param object The name of the object to deserialize
	 * @return The serialized JsonObject
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 * @throws FileNotFoundException
	 */
	public static JsonObject deseralizeObject(String object) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser jsonParser = new JsonParser();
		JsonObject rawSettings = jsonParser.parse(new FileReader(Settings.settingsFile)).getAsJsonObject();
		return rawSettings.get(object).getAsJsonObject();
	}
	
	/**
	 * Adds a JsonObject to the static HashMap<java.lang.String, com.google.gson.JsonObject> 
	 * of objects that will be serialized 
	 * when serialize() is called on an instance of this class
	 * @see #serialize()
	 * @param name The name of the object
	 * @param object The JsonObject to serialize
	 */
	public static void addObjectToSerialize(String name, JsonObject object) {
		objectsToSerialize.put(name, object);
	}
	
	/**
	 * Serialize the current instance, along with any objects added with addObjectToSerialize
	 * @see #addObjectToSerialize(String, JsonObject) 
	 * @throws IOException
	 */
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
	
	/**
	 * @return The name file specified in this instance
	 */
	public String getNameFile() {
		return nameFile;
	}
	
	/**
	 * @param nameFile The desired name file
	 */
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	
	/**
	 * @return The current session type
	 */
	public String getSessionType() {
		return sessionType;
	}
	
	/**
	 * @param sessionType The desired session type (individual or team)
	 */
	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}
	
	/**
	 * @return Whether or not Darklight Nova Web functionality is enabled
	 */
	public boolean isApiEnabled() {
		return apiEnabled;
	}
	
	/**
	 * @param apiEnabled Enable or disable Darklight Nova Web functionality
	 */
	public void setApiEnabled(boolean apiEnabled) {
		this.apiEnabled = apiEnabled;
	}
	
	/**
	 * @return The session ID used for the Darklight Nova Web API
	 */
	public int getApiID() {
		return apiID;
	}
	
	/**
	 * @param apiID The desired session ID to use for the Darklight Nova Web API
	 */
	public void setApiID(int apiID) {
		this.apiID = apiID;
	}
	
	/**
	 * @return The web protocol used by the Darklight Nova Web server
	 */
	public String getApiProtocol() {
		return apiProtocol;
	}
	
	/**
	 * @param apiProtocol The desired web protocol used by the Darklight Nova Web server (usually http)
	 */
	public void setApiProtocol(String apiProtocol) {
		this.apiProtocol = apiProtocol;
	}
	
	/**
	 * @return The URL of the Darklight Nova Web server
	 */
	public String getApiServer() {
		return apiServer;
	}
	
	/**
	 * @param apiServer The desired URL of the Darklight Nova Web server
	 */
	public void setApiServer(String apiServer) {
		this.apiServer = apiServer;
	}
	
	/**
	 * @return Whether or not name verification is enabled
	 */
	public boolean isVerificationEnabled() {
		return verificationEnabled;
	}
	
	/**
	 * @param verificationEnabled Enable or disable name verification
	 */
	public void setVerificationEnabled(boolean verificationEnabled) {
		this.verificationEnabled = verificationEnabled;
	}
	
	/**
	 * @return ArrayList of names that an input name is checked against if verification is enabled
	 * @see #isVerificationEnabled()
	 */
	public ArrayList<String> getVerificationNames() {
		return verificationNames;
	}
	
	/**
	 * @param verificationNames The names that will be used for verification
	 * @see #setVerificationEnabled(boolean) 
	 */
	public void setVerificationNames(ArrayList<String> verificationNames) {
		this.verificationNames = verificationNames;
	}
	
	/**
	 * Same as {@link #setVerificationEnabled(boolean)}, except for a team session
	 * @return ArrayList of teams that an input team is checked against if verification is enabled
	 */
	public ArrayList<String> getVerificationTeams() {
		return verificationTeams;
	}
	
	/**
	 * Same as {@link #setVerificationNames(ArrayList)}, except for a team session
	 * @param verificationTeams The team names that will be used for verification 
	 */
	public void setVerificationTeams(ArrayList<String> verificationTeams) {
		this.verificationTeams = verificationTeams;
	}
}
