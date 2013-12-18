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

public class JsonIntArray extends JsonArray<Long> {
    /**
     * Parse a string of JSON
     * @param json the string of JSON
     * @return a JsonIntArray object containing the parsed int array
     */
    public static JsonIntArray parse(String json) {
        JsonIntArray intArray;
        String[] tokens = json.split(":");
        ArrayList<Long> data = new ArrayList<Long>();
        for (String token : tokens[1].split(",")) {
            token = token.replace("[", "").replace("]", "").trim();
            if (!token.isEmpty())
                data.add(Long.parseLong(token));
        }
        intArray = new JsonIntArray(tokens[0].trim().replace("\"", ""), data);
        return intArray;
    }

    public JsonIntArray(String name) {
        super(name);
    }

    public JsonIntArray(String name, ArrayList<Long> data) {
        super(name, data);
    }
}
