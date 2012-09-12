package com.ijg.darklightnova.core;

import java.util.ArrayList;

public class AssessmentModule {
	private Engine engine;
	
	// What is returned with toString()
	final protected String name = "INSERT IMAGE NAME HERE";
	
	// Total issues
	public double total;
	
	// List of all fixed issues
	public ArrayList<Issue> issues = new ArrayList<Issue>();
	
	// List of the scoring modules
	public ArrayList<ScoreModule> modules = new ArrayList<ScoreModule>();
	
	public AssessmentModule(Engine engine) {
		this.engine = engine;
		/*
		 * add scoring modules
		 * to the vulnerability list
		 * here.
		 * Example:
		modules.add(new ExampleScoreingModule(this));*/
	}
	
	public void assess() {
		// Clear the issues list, add all fixed issues
		issues.clear();
		for (ScoreModule module : modules) {
			issues.addAll(module.check());
		}
		engine.writeFoundList(); // if there are newly found vulnerabilities, rewrite the progress file
	}
	
	public String toString() {
		return name;
	}
}
