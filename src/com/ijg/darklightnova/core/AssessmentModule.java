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
		modules.add(new ExampleScoringModule());*/
		
		for (ScoreModule m : modules) {
			total += m.getIssueCount();
		}
	}
	
	public void assess() {
		// Make any modifications necessary to the issues list
		boolean changed = false;
		for (ScoreModule module : modules) {
			ArrayList<Issue> modIssues = module.check();
			for (Issue issue : modIssues) {
				if (issue.fixed && !issues.contains(issue)) {
					issues.add(issue);
					changed = true;
				} else if (!issue.fixed && issues.contains(issue)) {
					issues.remove(issue);
					changed = true;
				}
			}
		}
		// If the issues list is changed, write a new progress file
		if (changed == true) {
			engine.writeFoundList();
		}
		
		//If somehow you broke the engine when boot happened and had no network, we can still create an account for you regardless of your faulty connection at first.
		if (!engine.database.userExists(engine.getUserName(), engine.sessionid)) {
			engine.database.insertNewUser(engine.getUserName(), engine.sessionid);
		}
		//Regardless of if it changed or not, send an update. This way, a loss in internet connection won't mean you're locked out until your score changes.
		engine.database.updateUserScore(engine.getUserName(), engine.sessionid, issues.size());
		
	}
	
	public String toString() {
		return name;
	}
}
