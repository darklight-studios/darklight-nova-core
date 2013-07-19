package com.ijg.darklight.sdk.core;

/*
 * Copyright (C) 2013  Isaac Grant
 * 
 * This file is part of the Darklight Nova Core.
 *  
 * Darklight Nova Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Darklight Nova Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Darklight Nova Core.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Issues should be subclassed from this, and implement the following methods:<br />
 * <ul><li>void {@link #install()}</li><li>boolean {@link #isFixed()}</li></ul>
 * @author Isaac Grant
 */

public abstract class Issue {
	private String name;
	private String description;
	
	protected Issue(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	
	/**
	 * Method to install/create this issue on target machine,
	 * should be overridden.
	 */
	public static void install() {}
	
	/**
	 * If this issue implements any external settings
	 * then they should be loaded here.<br />
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
	 * Protected setter for issue description, so that issues that are
	 * defined by loaded settings can call super(name, description) in
	 * the constructor as required, then change the description after 
	 * the settings have been loaded
	 * @param description The desired description of this issue
	 */
	protected void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return The name of this issue
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The description of this issue
	 */
	public String getDescription() {
		return description;
	}
}
