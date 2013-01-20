package com.ijg.darklight.sdk.core;

import java.util.ArrayList;

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
	 * Get an ArrayList of fixed issues
	 * @return An ArrayList of fixed issues
	 */
	public ArrayList<Issue> getFixedIssues() {
		return engine.moduleHandler.getIssues();
	}
}
