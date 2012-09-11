package com.ijg.darklightnova.engine;

import java.util.ArrayList;

public class ScoreModule {
	protected ArrayList<Issue> issues;
	
	/*
	 * All scoring modules are subclassed from
	 * this
	 */
	
	public ScoreModule() {}
	
	public ArrayList<Issue> check() {
		return null;
	}
	
	protected void add(ArrayList<Issue> issues, Issue issue) {
		if (!issue.fixed) {
			if (issues.contains(issue)) {
				issues.get(issues.indexOf(issue)).fixed = true;
			} else {
				issue.fixed = true;
				issues.add(issue);
			}
		} else if (!issues.contains(issue)) {
			issues.add(issue);
		}
	}
	
	protected void remove(ArrayList<Issue> issues, Issue issue) {
		/*
		 * TODO: finish
		 */
	}
}
