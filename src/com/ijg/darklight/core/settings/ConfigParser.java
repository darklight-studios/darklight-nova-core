package com.ijg.darklight.core.settings;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * Configuration file parser
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class ConfigParser {
	private static File configFile = new File(new File("."), "config.json");
	private static boolean parsed = false;
	private static JsonObject config;
	
	/**
	 * Parse the config file
	 */
	public static void start() {
		try {
			
			Scanner s = new Scanner(configFile);
			String configRaw = "";
			while (s.hasNextLine()) {
				configRaw += s.nextLine();
			}
			s.close();
			JsonParser jsonParser = new JsonParser();			
			config = (JsonObject) jsonParser.parse(configRaw);
			parsed = true;
		} catch (IOException e) {
			System.out
					.println("Error parsing config file, dumping stack trace...");
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the value defined under the given category and key
	 * @param category The JSON category the key is under
	 * @param key The JSON key for the desired value
	 * @return The value of the category and key parsed from the JSON value, as an object
	 */
	public static Object getValue(String category, String key) {
		if (!parsed) {
			start();
		}
		return (Object) ((JsonObject) config.get(category)).get(key);
	}
	
	/**
	 * Clear memory
	 */
	public static void destroy() {
		config = null;
		parsed = false;
	}
	
	/**
	 * Get the config file as a JsonObject
	 * @return The config file as a JsonObject
	 */
	public static JsonObject getConfig() {
		return config;
	}
	
	/**
	 * 
	 * @return True if the config file has been parsed and destroy() has not yet been called
	 */
	public static boolean getParsed() {
		return parsed;
	}
}
