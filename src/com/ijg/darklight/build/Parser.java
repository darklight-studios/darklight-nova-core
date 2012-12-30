package com.ijg.darklight.build;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Darklight build file parser
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class Parser {
	private File buildFile;
	private JSONObject rawData;
	private boolean dataReady = false;
	
	/**
	 * 
	 * @param buildFile The Darklight build file
	 */
	public Parser(File buildFile) {
		this.buildFile = buildFile;
	}
	
	/**
	 * Parse the file into a JSONObject
	 */
	public void parse() {
		try {
			rawData = (JSONObject) JSONValue.parse(new FileReader(buildFile));
			dataReady = true;
		} catch (FileNotFoundException e) {
			dataReady = false;
			System.out
					.println("[DarklightInstaller] Error: Build file not found");
		}
	}
	
	/**
	 * Query the JSONObject parsed from the build file
	 * @param key The key of the desired value
	 * @return The value given the key
	 */
	public Object get(String key) {
		if (dataReady) {
			return rawData.get(key);
		} else {
			parse();
			return rawData.get(key);
		}
	}
	
	/**
	 * Clear memory
	 */
	public void destroy() {
		rawData = null;
		dataReady = false;
	}
}
