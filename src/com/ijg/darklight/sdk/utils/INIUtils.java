package com.ijg.darklight.sdk.utils;

import java.util.ArrayList;

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

/**
 * Utilities to access data in INI files, designed to be used alongside
 * with {@link FileLoader}
 * @author Isaac Grant
 */

public class INIUtils {
	
	public static final String NOT_FOUND = "[value not found]";
	
	/**
	 * Search for the value of a key in a tokenized ini file
	 * @param token The key to find the value of
	 * @param iniTokens The tokenized ini file
	 * @return The value of the key, or {@link #NOT_FOUND} if the key doesn't exist
	 */
	public static String search(String token, ArrayList<byte[]> iniTokens) {
		String pToken = "";
		for (byte[] iniTokenBytes : iniTokens) {
			String curToken = new String(iniTokenBytes);
			if (curToken.equals(token)) {
				pToken = curToken;
			} else if (pToken.equals(token)) {
				if (curToken.equals("=")) {
					pToken = curToken;
				}
			} else if (pToken.equals("=")) {
				return curToken;
			} else if (curToken.contains(token) && curToken.contains("=")) {
				return curToken.substring(curToken.indexOf("=")+1, curToken.length());
			} else {
				pToken = "";
			}
		}
		return NOT_FOUND; 
	}
}
