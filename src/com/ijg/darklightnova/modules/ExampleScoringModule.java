package com.ijg.darklightnova.modules;

import java.util.ArrayList;

import com.ijg.darklightnova.core.Issue;
import com.ijg.darklightnova.core.ScoreModule;

public class ExampleScoringModule extends ScoreModule {
	/*
	 * This is an example scoring module
	 */
	
	private Issue exampleIssue = new Issue("Example Vulnerability", "This is an example vulnerability.");
	private Issue exampleIssue2 = new Issue("Example Vulnerability", "This is an example vulnerability.");
	private Issue exampleIssue3 = new Issue("Example Vulnerability", "This is an example vulnerability.");
	
	
	public ExampleScoringModule() {
		issues.add(exampleIssue);
		issues.add(exampleIssue2);
		issues.add(exampleIssue3);
	}
	
	private boolean fixedExampleVulnerability() {
		/*
		 * Private methods are used to check whether
		 * or not the vulnerability has been found, usually
		 * they are booleans, though sometimes other
		 * return types are warranted.
		 */
		return true;
	}
	
	private boolean fixed2() {
		return false;
	}
	
	private boolean fixed3() {
		return false;
	}
	
	public ArrayList<Issue> check() {
		if (fixedExampleVulnerability()) {
			add(exampleIssue);
		} else {
			remove(exampleIssue);
		}
		
		if (fixed2()) {
			add(exampleIssue2);
		} else {
			remove(exampleIssue3);
		}
		
		if (fixed3()) {
			add(exampleIssue3);
		} else {
			remove(exampleIssue3);
		}
		return issues;
	}
}
