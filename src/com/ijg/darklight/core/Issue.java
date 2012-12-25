package com.ijg.darklight.core;

/**
 * Issue object, provides a way to track security issues
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class Issue {
	private String name;
	private String description;
	private boolean fixed;
	
	/**
	 * 
	 * @param name The name of the issue
	 * @param description A description of the issue
	 */
	public Issue(String name, String description) {
		this.name = name;
		this.description = description;
		fixed = false;
	}
	
	/**
	 * Set the fixed variable
	 * @param fixed Define whether or not the issue is fixed
	 */
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	/**
	 * Get the status of the issue
	 * @return True if the issue has been fixed
	 */
	public boolean getFixed() {
		return fixed;
	}
	
	/**
	 * Get the name of the issue
	 * @return The name of the issue
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the description of the issue
	 * @return The description of the issue
	 */
	public String getDescription() {
		return description;
	}
}