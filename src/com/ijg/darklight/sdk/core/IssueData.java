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
 * How issue data (name & description) are stored, used to remedy
 * problem with issues that have the same name
 * 
 * @author Isaac Grant
 *
 */

public class IssueData {

	private String name, description;
	
	public IssueData(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * @return The issue's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Alter the issue's description after initiation
	 * @param description The new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return The issue's description
	 */
	public String getDescription() {
		return description;
	}
}
