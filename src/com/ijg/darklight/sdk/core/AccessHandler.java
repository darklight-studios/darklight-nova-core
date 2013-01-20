package com.ijg.darklight.sdk.core;

import java.util.ArrayList;

public class AccessHandler {
	private CoreEngine engine;
	
	public AccessHandler(CoreEngine engine) {
		this.engine = engine;
	}
	
	public Settings getSettings() {
		return engine.settings;
	}
	
	public ArrayList<Issue> getFixedIssues() {
		return engine.moduleHandler.getIssues();
	}
}
