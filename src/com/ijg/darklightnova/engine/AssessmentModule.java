package com.ijg.darklightnova.engine;

import java.util.ArrayList;

public class AssessmentModule {
	private Engine engine;
	
	// Accessed with toString(), used in the title of the GUI window
	final protected String name = "INSERT IMAGE NAME HERE";
	
	// Total vulnerabilities
	public double total;
	
	// List of all found vulnerabilities
	public ArrayList<Issue> issues = new ArrayList<Issue>();
	
	// List of the scoring modules used
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
		for (ScoreModule module : modules) {
			issues.addAll(module.check());
		}
		engine.writeFoundList(); // if there are newly found vulnerabilities, rewrite the progress file
	}
	
	public String toString() {
		return name;
	}
}
