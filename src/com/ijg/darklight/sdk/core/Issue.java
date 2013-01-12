package com.ijg.darklight.sdk.core;

public class Issue {
	private String name;
	private String description;
	private boolean fixed;
	
	public Issue(String name, String description) {
		this.name = name;
		this.description = description;
		fixed = false;
	}
	
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	public boolean getFixed() {
		return fixed;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
