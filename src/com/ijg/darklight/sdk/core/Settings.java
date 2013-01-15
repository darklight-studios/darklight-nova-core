package com.ijg.darklight.sdk.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Settings {
	private static File configFile = new File(new File("."), "config.json");
	private static JsonObject config;
	private static boolean parsed = false;
	
	/**
	 * Load the config file as a JsonObject
	 */
	public static void parse() {
		if (!parsed) {
			JsonParser parser = new JsonParser();
			try {
				config = parser.parse(new FileReader(configFile)).getAsJsonObject();
				parsed = true;
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				System.err.println("Error parsing config file");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get property value as a string
	 * @param category Category of the property
	 * @param key Property name
	 * @return The value of the property
	 */
	public static String getProperty(String category, String key) {
		parse();
		return config.get(category).getAsJsonObject().get(key).getAsString();
	}
	
	/**
	 * Get property value as a boolean
	 * @param category Category of the property
	 * @param key Property name
	 * @return The value of the property
	 */
	public static boolean getPropertyAsBool(String category, String key) {
		parse();
		return config.get(category).getAsJsonObject().get(key).getAsBoolean();
	}
	
	/**
	 * Get property value as an integer
	 * @param category Category of the property
	 * @param key Property name
	 * @return The value of the property
	 */
	public static int getPropertyAsInt(String category, String key) {
		parse();
		return config.get(category).getAsJsonObject().get(key).getAsInt();
	}
	
	/**
	 * Get property value as a JsonArray
	 * @param category Category of the property
	 * @param key Property name
	 * @return The value of the property
	 */
	public static JsonArray getPropertyAsJsonArray(String category, String key) {
		parse();
		return config.get(category).getAsJsonObject().get(key).getAsJsonArray();
	}
	
	/**
	 * Get a JsonObject from the config file
	 * @param name The name of the JsonObject in the config file
	 * @return The JsonObject with the given name
	 */
	public static JsonObject getSubObject(String name) {
		parse();
		return config.get(name).getAsJsonObject();
	}
}
