package com.ijg.darklight.core.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.google.gson.Gson;

public class ConfigFileParser {

	// You can just get the configuration file and check settings here
	public ConfigFile getConfiguration() {
		return configuration;
	}

	public void setConfiguration(ConfigFile configuration) {
		this.configuration = configuration;
	}

	File configurationPath;
	ConfigFile configuration;
	
	public ConfigFileParser(File path) {
		this.configurationPath = path;
	}
	
	public void loadConfigFile() {
		Scanner s;
		try {
			s = new Scanner(configurationPath);
			
			String rawJson = "";
			while (s.hasNextLine()) {
				rawJson += s.nextLine();
			}
			
			s.close();
			
			Gson gson = new Gson();
			
			configuration = gson.fromJson(rawJson, ConfigFile.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void saveConfigurationFile() {
		// Serialize the config file back and write it
	}
}
