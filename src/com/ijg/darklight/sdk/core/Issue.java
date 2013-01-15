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
	
	/**
	 * Set whether or not this issue has been fixed
	 * @param fixed If this issue has been fixed or not
	 */
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	/**
	 * Check whether or not this issue has been fixed
	 * @return True if this issue has been fixed
	 */
	public boolean getFixed() {
		return fixed;
	}
	
	/**
	 * Get this issue's name
	 * @return The name of this issue
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the issue's description
	 * @return The description of this issue
	 */
	public String getDescription() {
		return description;
	}
}
