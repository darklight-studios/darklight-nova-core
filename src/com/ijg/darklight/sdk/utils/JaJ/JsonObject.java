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

public class JsonObject extends JsonData {
	private ArrayList<JsonData> data;
	
	public static JsonObject parse(String json) {
		JsonObject object;
		String name = json.split(":")[0].replace("\"", "").trim();
		json = json.substring(name.length()+4).replace("{", "").replace("}", "").trim();
		String[] tokens = json.split(",");
		ArrayList<JsonData> data = new ArrayList<JsonData>();
		for (String token : tokens) {
			switch (JaJ.classify(token.split(":")[1])) {
			case PRIMITIVE:
				data.add(JsonPrimitive.parse(token));
				break;
			case STRING:
				data.add(JsonString.parse(token));
				break;
			case ARRAY:
				switch (JaJ.classifyArray(token)) {
				case PRIM_ARRAY:
					data.add(JsonIntArray.parse(token));
					break;
				case STRING_ARRAY:
					data.add(JsonStringArray.parse(token));
					break;
				default: break;
				}
				break;
			case OBJECT:
				data.add(JsonObject.parse(token));
				break;
			default: break;
			}
		}
		object = new JsonObject(name, data);
		return object;
	}
	
	public JsonObject(String name) {
		super(name);
		data = new ArrayList<JsonData>();
	}
	
	public JsonObject(String name, ArrayList<JsonData> data) {
		super(name);
		this.data = data;
	}
	
	public void addData(JsonData newData) {
		data.add(newData);
	}
	
	public String jsonify() {
		String json;
		String dataString = "";
		for (int i = 0; i < data.size() - 1; i++) {
			dataString += '\t' + data.get(i).jsonify() + ",\n";
		}
		if (data.size() > 0) dataString += '\t' + data.get(data.size() - 1).jsonify() + '\n';
		else dataString += "\n";
		json = '\"' + getName() + "\": {\n" + dataString + '}';
		return json;
	}
}
