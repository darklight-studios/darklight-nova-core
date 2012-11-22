package com.ijg.darklight.core.settings;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Parser {
	private static File configFile = new File(new File("."), "config");
	private static boolean parsed = false;
	private static JSONObject config;
	
	public static void start() {
		try {
			config = (JSONObject) JSONValue.parse(new FileReader(configFile));
			parsed = true;
		} catch (IOException e) {
			System.out
					.println("Error parsing config file, dumping stack trace...");
			e.printStackTrace();
		}
	}
	
	public static Object getValue(String category, String key) {
		if (!parsed) {
			start();
		}
		return (Object) ((JSONObject) config.get(category)).get(key);
	}
	
	public static void destroy() {
		config = null;
		parsed = false;
	}
	
	public static JSONObject getConfig() {
		return config;
	}
	
	public static boolean getParsed() {
		return parsed;
	}
}
