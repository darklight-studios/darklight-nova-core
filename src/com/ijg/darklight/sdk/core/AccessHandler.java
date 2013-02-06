package com.ijg.darklight.sdk.core;

import java.util.HashMap;

public class AccessHandler {
	private CoreEngine engine;
	
	/**
	 * 
	 * @param engine The instance of CoreEngine this AccessHandler will use
	 */
	public AccessHandler(CoreEngine engine) {
		this.engine = engine;
	}
	
	/**
	 * Get the instance of Settings currently used by the CoreEngine
	 * @return The instance of Settings currently being used
	 */
	public Settings getSettings() {
		return engine.settings;
	}
	
	/**
	 * Get a HashMap of fixed issues
	 * @return A HashMap of fixed issues, key is issue name, value is issue description
	 */
	public HashMap<String, String> getFixedIssues() {
		return engine.issueHandler.getFixedIssues();
	}
}
