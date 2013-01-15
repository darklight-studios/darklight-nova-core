package com.ijg.darklight.sdk.core;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class ScoreModule {
	
	protected ArrayList<Issue> issues = new ArrayList<Issue>();
	protected abstract void loadSettings();
	public abstract ArrayList<Issue> check();
	
	/**
	 * Update an issue in the ArrayList to be fixed
	 * @param issue The issue to set as fixed
	 */
	protected void add(Issue issue) {
		purgeIssue(issue);
		if (!issue.getFixed()) issue.setFixed(true);
		issues.add(issue);
	}
	
	/**
	 * Update an issue in the ArrayList to be set as not fixed
	 * @param issue The issue to set as not fixed
	 */
	protected void remove(Issue issue) {
		purgeIssue(issue);
		if (issue.getFixed()) issue.setFixed(false);
		issues.add(issue);
	}
	
	/**
	 * Remove an issue from the ArrayList to be updated and re-added
	 * @param issue
	 */
	protected void purgeIssue(Issue issue) {
		Iterator<Issue> i = issues.iterator();
		
		while (i.hasNext()) {
			Issue oldIssue = i.next();
			if (oldIssue.getName().equals(issue.getName())) {
				i.remove();
			}
		}
	}
	
	/**
	 * Get the number of issues this module contains
	 * @return The number of issues this module contains
	 */
	public int getIssueCount() {
		return issues.size();
	}
}
