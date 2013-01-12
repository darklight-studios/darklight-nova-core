package com.ijg.darklight.sdk.core;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class ScoreModule {
	
	protected ArrayList<Issue> issues = new ArrayList<Issue>();
	protected abstract void loadSettings();
	public abstract ArrayList<Issue> check();
	
	protected void add(Issue issue) {
		purgeIssue(issue);
		if (!issue.getFixed()) issue.setFixed(true);
		issues.add(issue);
	}
	
	protected void remove(Issue issue) {
		purgeIssue(issue);
		if (issue.getFixed()) issue.setFixed(false);
		issues.add(issue);
	}
	
	protected void purgeIssue(Issue issue) {
		Iterator<Issue> i = issues.iterator();
		
		while (i.hasNext()) {
			Issue oldIssue = i.next();
			if (oldIssue.getName().equals(issue.getName())) {
				i.remove();
			}
		}
	}
	
	public int getIssueCount() {
		return issues.size();
	}
}
