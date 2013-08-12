package com.ijg.darklight.sdk.core;

/*
 * Copyright (C) 2013  Isaac Grant
 * 
 * This file is part of the Darklight Nova Core.
 *  
 * Darklight Nova Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Darklight Nova Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Darklight Nova Core.  If not, see <http://www.gnu.org/licenses/>.
 */


public class AccessHandler {
	private CoreEngine engine;
	
	/**
	 * @param engine The instance of {@link CoreEngine} this AccessHandler will use
	 */
	public AccessHandler(CoreEngine engine) {
		this.engine = engine;
	}
	
	/**
	 * @return Finished status of the session
	 */
	public boolean isFinished() {
		return engine.finished();
	}
	
	/**
	 * @return The total number of issues
	 */
	public int getTotalIssueCount() {
		return engine.issueHandler.getTotalIssueCount();
	}
	
	/**
	 * @return The number of fixed issues
	 */
	public int getFixedIssueCount() {
		return engine.issueHandler.getFixedIssueCount();
	}
	
	/**
	 * @return The percent complete (percent of issues fixed)
	 */
	public String getPercentComplete() {
		return engine.issueHandler.getFixedIssuePercent();
	}
	
	/**
	 * Get an array of fixed issues' IssueData
	 * @return A HashMap of fixed issues, key is issue name, value is issue description
	 */
	public IssueData[] getFixedIssues() {
		return engine.issueHandler.getFixedIssues();
	}
	
	/**
	 * Finish the session
	 */
	public void finishSession() {
		engine.finishSession();
	}
	
	/**
	 * Call CoreEngine.update()
	 */
	public void checkIssues() {
		engine.update();
	}
	
	/**
	 * @param autoUpdate Whether or not the CoreEngine should automatically call CoreEngine.update()
	 */
	public void setAutoUpdate(boolean autoUpdate) {
		engine.setAutoUpdate(autoUpdate);
	}
	
	/**
	 * Change the template used to create the score output
	 * @param file The template file to be used
	 */
	public void setTemplateFile(String file) {
		engine.outputManager.setTemplateName(file);
	}
	
	/**
	 * Change the extension of the score output file
	 * @param extension Extension to be used (txt, html, etc)
	 */
	public void setOutputFileExtension(String extension) {
		engine.outputManager.setOutputExt(extension);
	}
}
