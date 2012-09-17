package com.ijg.darklightnova.live;

import java.util.ArrayList;

public abstract class ScoreModule {
	/*
	 * All scoring modules are subclassed from
	 * this
	 */
	protected ArrayList<Issue> issues = new ArrayList<Issue>();
	public abstract ArrayList<Issue> check();
	
	protected void add(Issue issue) {
		if (!issue.fixed) issue.fixed = true;
		issues.add(issue);
	}
	
	protected void remove(Issue issue) {
		if (issue.fixed) issue.fixed = false;
		issues.add(issue);
	}
}
