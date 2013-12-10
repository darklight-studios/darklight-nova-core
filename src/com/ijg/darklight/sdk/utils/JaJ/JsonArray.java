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

public class JsonArray<T> extends JsonData {
	public ArrayList<T> data;
	
	public JsonArray(String name) {
		super(name);
		data = new ArrayList<T>();
	}
	
	public JsonArray(String name, ArrayList<T> data) {
		super(name);
		this.data = data;
	}
	
	public void add(T newData) {
		data.add(newData);
	}
	
	public String jsonify() {
		String json = "\"" + getName() + "\": [";
		for (int i = 0; i < data.size() - 1; i++) {
			json += data.get(i) + ", ";
		}
		if (data.size() > 0) json += data.get(data.size() - 1) + "]";
		else json += "]";
		return json;
	}
}
