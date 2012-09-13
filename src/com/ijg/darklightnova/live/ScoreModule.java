package com.ijg.darklightnova.live;

import java.util.ArrayList;

public class ScoreModule {
	/*
	 * All scoring modules are subclassed from
	 * this
	 */
	
	public ScoreModule() {}
	
	public ArrayList<Issue> check() {
		/*
		 * Run private methods to check the status
		 * of the issues, those whose checks returned
		 * true but have the fixed boolean as false
		 * will be added to the returned list, and
		 * fixed will be switched to true
		 * 
		 * Issues whose checks return false but fixed
		 * booleans are true will be added to the return
		 * list, with their fixed boolean changed to false
		 * 
		 * For example:
		 * if (issueFound()) {
		 *     add(issues, issue);
		 * else {
		 *     remove(issues, issue);
		 * 
		 * (see add and remove functions)
		 * See com.ijg.darklightnova.modules.ExampleScoringModule
		 * 
		 * Then the AssessmentModule will handle, based on
		 * all of the issues returned and the status of their
		 * fixed boolean what is going on (lost a point, gained
		 * points, etc)
		 * 
		 * Is there a better way to do this?
		 */
		
		/*
		 * Different way to do this using the weights?
		 */
		
		return new ArrayList<Issue>();
	}
	
	protected void add(ArrayList<Issue> issues, Issue issue) {
		if (!issue.fixed) issue.fixed = true;
		issues.add(issue);
	}
	
	protected void remove(ArrayList<Issue> issues, Issue issue) {
		if (issue.fixed) issue.fixed = false;
		issues.add(issue);
	}
}
