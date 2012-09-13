package com.ijg.darklightnova.live;

public class Issue {
	public String name;
	public String description;
	public boolean fixed;
	// Weight is how the issue individually effects the score
	public double weight = 1D;
	
	public Issue(String name, String description) {
		this.name = name;
		this.description = description;
		fixed = false;
	}
	
	public Issue(String name, String description, Double weight) {
		this.name = name;
		this.description = description;
		this.weight = weight;
		fixed = false;
	}
}