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

public class JsonPrimitive extends JsonData {
	private long data;
	
	public static JsonPrimitive parse(String json) {
		JsonPrimitive primitive;
		String[] tokens = json.split(":");
		primitive = new JsonPrimitive(tokens[0].trim().replace("\"", ""), Long.parseLong(tokens[1].trim().replace(",", "")));
		return primitive;
	}
	
	public JsonPrimitive(String name, long data) {
		super(name);
		this.data = data;
	}
	
	public String jsonify() {
		return "\"" + getName() + "\": " + data;
	}
}
