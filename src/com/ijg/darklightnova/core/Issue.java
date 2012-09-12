package com.ijg.darklightnova.core;

public class Issue {
	public String name;
	public String description;
	public boolean fixed;
	
	public Issue(String name, String description) {
		this.name = name;
		this.description = description;
		fixed = false;
	}
}