package com.ijg.darklight.build;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Darklight build file parser
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class Parser {
	private File buildFile;
	private JsonObject rawData;
	private boolean dataReady = false;
	
	/**
	 * 
	 * @param buildFile The Darklight build file
	 */
	public Parser(File buildFile) {
		this.buildFile = buildFile;
	}
	
	/**
	 * Parse the file into a JsonObject
	 */
	public void parse() {
		try {
			Scanner s = new Scanner(buildFile);
			String rawJson = "";
			while (s.hasNextLine()) {
				rawJson += s.nextLine();
			}
			s.close();
			JsonParser jsonParser = new JsonParser();			
			rawData = (JsonObject) jsonParser.parse(rawJson);
			dataReady = true;
		} catch (FileNotFoundException e) {
			dataReady = false;
			System.out
					.println("[DarklightInstaller] Error: Build file not found");
		}
	}
	
	/**
	 * Query the JsonObject parsed from the build file
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
