package com.ijg.darklight.sdk.core;

import java.util.ArrayList;
import java.util.HashMap;

import me.shanked.nicatronTg.darklight.view.VulnerabilityOutput;

/**
 * Handles issues, and the generation of a status file
 * 
 * @see me.shanked.nicatronTg.darklight.view.VulnerabilityOutput
 * @author Isaac Grant
 *
 */

public class IssueHandler {
	private HashMap<String, String> fixedIssues = new HashMap<String, String>();
	private ArrayList<Issue> issues;
	
	private VulnerabilityOutput outputManager;
	
	public IssueHandler(ArrayList<Issue> loadedIssues) {
		outputManager = new VulnerabilityOutput(this);
		
		issues = loadedIssues;
	}
	
	public void checkAllIssues() {
		for (Issue issue : issues) {
			if (issue.isFixed()) {
				if (!fixedIssues.containsKey(issue.getName())) {
					fixedIssues.put(issue.getName(), issue.getDescription());
				}
			} else {
				if (fixedIssues.containsKey(issue.getName())) {
					fixedIssues.remove(issue.getName());
				}
			}
		}
		outputManager.writeNewOutput();
	}
	
	/**
	 * @return HashMap of fixed issues, key is issue name, value is issue description
	 */
	public HashMap<String, String> getFixedIssues() {
		return fixedIssues;
	}
	
	/**
	 * @return Total number of issues
	 */
	public int getTotalIssueCount() {
		return issues.size();
	}
	
	/**
	 * @return Number of fixed issues
	 */
	public int getFixedIssueCount() {
		return fixedIssues.size();
	}
	
	/**
	 * @return The percentage of issues fixed as a string
	 */
	public String getFixedIssuePercent() {
		return "" + Math.round((fixedIssues.size() / ((double) issues.size()) * 100)) + "%";
	}
}
