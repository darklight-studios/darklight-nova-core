package com.ijg.darklight.sdk.utils.JaJ;

/*
 * This file is part of JaJ.
 *
 * JaJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JaJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JaJ.  If not, see <http://www.gnu.org/licenses/>.
 */

public class JsonString extends JsonData {
	private String data;
	
	public static JsonString parse(String json) {
		JsonString string;
		String[] tokens = json.split(":");
		String data = tokens[1].trim().replace("\"", "");
		if (data.endsWith(",")) data = data.substring(0, data.length() - 2);
		string = new JsonString(tokens[0].trim().replace("\"", ""), data);
		return string;
	}
	
	public JsonString(String name, String data) {
		super(name);
		this.data = data;
	}
	
	public String jsonify() {
		return "\"" + getName() + "\": \"" + data + "\"";
	}
}
