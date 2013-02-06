package com.ijg.darklight.sdk.core;

public abstract class Issue {
	private String name;
	private String description;
	
	private Issue(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	
	/**
	 * Method to install/create this issue on target machine,
	 * should be overriden.
	 */
	public static void install() {}
	
	/**
	 * If this issue implements any settings found in the config.json
	 * file, then they are loaded here
	 */
	protected abstract void loadSettings();
	
	/**
	 * Check whether or not this issue is fixed, the content
	 * of this function will depend on how to check the status
	 * of a given issue
	 * @return If this issue has been fixed or not
	 */
	public abstract boolean isFixed();
	
	/**
	 * 
	 * @return The name of this issue
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return The description of this issue
	 */
	public String getDescription() {
		return description;
	}
}
