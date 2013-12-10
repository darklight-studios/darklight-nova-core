package com.ijg.darklight.sdk.utils.JaJ;

import java.util.ArrayList;

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

public class JsonStringArray extends JsonArray<String> {
	
	public static JsonStringArray parse(String json) {
		JsonStringArray stringArray;
		String[] tokens = json.split(":");
		ArrayList<String> data = new ArrayList<String>();
		for (String token : tokens[1].split(",")) {
			data.add(token.trim().replace("\"", "").replace("[", "").replace("]", ""));
		}
		stringArray = new JsonStringArray(tokens[0].trim().replace("\"", ""), data);
		return stringArray;
	}
	
	public JsonStringArray(String name) {
		super(name);
	}
	
	public JsonStringArray(String name, ArrayList<String> data) {
		super(name, data);
	}

	@Override
	public String jsonify() {
		String json = "\"" + getName() + "\": [";
		for (int i = 0; i < data.size() - 1; i++) {
			json += "\"" + data.get(i) + "\", ";
		}
		json += "\"" + data.get(data.size() - 1) + "\"]";
		return json;
	}
}
