package com.ijg.darklight.core.settings;

import java.io.File;
import java.io.IOException;

import org.ini4j.Wini;

public class Parser {
	static String configFile = System.getProperty("user.dir");
	static int progress = 0;
	static Wini config;
	
	public static void start() {
		try {
			config = new Wini(new File(configFile));
			++progress;
		} catch (IOException e) {
			System.out.println("Error reading config file, stack trace:\n" + e.getStackTrace());
			// Better way to kill?
			System.exit(1);
		}
	}
	
	public static String getValue(String category, String key) {
		if (progress == 0) {
			start();
		} else if (progress == 10) {
			return destroyAndReturn(config.get(category, key));
		}
		++progress;
		return config.get(category, key);
	}
	
	private static String destroyAndReturn(String value) {
		config = null;
		return value;
	}
}
