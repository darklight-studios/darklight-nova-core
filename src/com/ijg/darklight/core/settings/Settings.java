package com.ijg.darklight.core.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class Settings {
	private static File configFile = new File(new File("."), "config.json");
	private static JsonObject config;
	private static boolean parsed = false;
	
	public static void init() {
		JsonParser parser = new JsonParser();
		try {
			config = parser.parse(new FileReader(configFile)).getAsJsonObject();
			parsed = true;
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String category, String key) {
		if (parsed) {
			return config.get(category).getAsJsonObject().get(key).getAsString();
		} else {
			init();
			return config.get(category).getAsJsonObject().get(key).getAsString();
		}
	}
	
	public static boolean getPropertyAsBool(String category, String key) {
		if (parsed) {
			return config.get(category).getAsJsonObject().get(key).getAsBoolean();
		} else {
			init();
			return config.get(category).getAsJsonObject().get(key).getAsBoolean();
		}
	}
	
	public static int getPropertyAsInt(String category, String key) {
		if (parsed) {
			return config.get(category).getAsJsonObject().get(key).getAsInt();
		} else {
			init();
			return config.get(category).getAsJsonObject().get(key).getAsInt();
		}
	}
	
	public static JsonArray getPropertyAsJsonArray(String category, String key) {
		if (parsed) {
			return config.get(category).getAsJsonObject().get(key).getAsJsonArray();
		} else {
			init();
			return config.get(category).getAsJsonObject().get(key).getAsJsonArray();
		}
	}
	
	public static JsonObject getSubObject(String name) {
		if (parsed) {
			return config.get(name).getAsJsonObject();
		} else {
			init();
			return config.get(name).getAsJsonObject();
		}
	}
}
