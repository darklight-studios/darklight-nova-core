package com.ijg.darklightnova.live;

import java.util.ArrayList;

import com.ijg.darklightnova.core.Issue;
import com.ijg.darklightnova.core.ScoreModule;

public class AssessmentModule {
	// What is returned with toString()
	final protected String name = "INSERT IMAGE NAME HERE";
	
	// Total issues
	public double total;
	
	// List of all found issues
	public ArrayList<Issue> issues = new ArrayList<Issue>();
	
	// List of the scoring modules used
	public ArrayList<ScoreModule> modules = new ArrayList<ScoreModule>();
	
	public AssessmentModule() {
		/*
		 * add scoring modules
		 * to the vulnerability list
		 * here.
		 * Example:
		modules.add(new ExampleScoreingModule());*/
	}
	
	public void assess() {
		/*
		 * TODO: Account for score based on issue weight?
		 */
		// Make any modifications necessary to the issues list
		for (ScoreModule module : modules) {
			ArrayList<Issue> modIssues = module.check();
			for (Issue issue : modIssues) {
				if (issue.fixed && !issues.contains(issue)) {
					issues.add(issue);
				} else if (!issue.fixed && issues.contains(issue)) {
					issues.remove(issue);
				}
			}
		}
	}
	
	public String toString() {
		return name;
	}
}
