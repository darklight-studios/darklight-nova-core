package com.ijg.darklight.sdk.utils.JaJ;

import java.util.ArrayList;

/*
 * JaJ - Object Oriented JSON in Java
 * Copyright (C) 2013  Isaac Grant
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * JaJ - Object Oriented JSON in java
 *
 * @author Isaac Grant
 */
public class JaJ {
    /**
     * Turn an ArrayList of JsonData to a (somewhat) formatted string
     * @param data ArrayList to turn into a string
     * @return The formatted string
     */
    public static String jsonify(ArrayList<JsonData> data) {
        String json = "{\n";
        for (int i = 0; i < data.size() - 1; i++) {
            json += '\t' + data.get(i).jsonify().replace("\n", "\n\t") + ",\n";
        }
        json += '\t' + data.get(data.size() - 1).jsonify() + "\n}";
        return json;
    }

    /**
     * Load a json string into an ArrayList of JsonData
     * @param json JSON string
     * @return ArrayList of parsed JsonData
     */
    @SuppressWarnings("incomplete-switch")
    public static ArrayList<JsonData> load(String json) {
        ArrayList<JsonData> data = new ArrayList<JsonData>();
        json = json.trim().substring(1).substring(0, json.length()-2).trim();
        json = json.replace("\t", "").replace("\n", "").replace("\r", "");
        String[] parts = json.split(",");
        int i = 0;
        boolean lookingForArray = false;
        while (i < parts.length) {
            if (parts[i].contains("[")) {
                int j = i + 1;
                lookingForArray = true;
                while (j < parts.length && lookingForArray) {
                    if (parts[j].contains("]")) {
                        lookingForArray = false;
                        String built = parts[i];
                        for (int k = i+1; k < j+1; k++) {
                            built += ", " + parts[k].trim();
                        }
                        parts[i] = built;
                        int k = j;
                        while (k > i) {
                            parts[k] = "";
                            k--;
                        }
                    }
                    j++;
                }
            }
            i++;
        }
        i = 0;
        lookingForArray = false;
        while (i < parts.length) {
            if (parts[i].contains("{")) {
                int j = i + 1;
                lookingForArray = true;
                while (j < parts.length && lookingForArray) {
                    if (parts[j].contains("}")) {
                        lookingForArray = false;
                        String built = parts[i];
                        for (int k = i+1; k < j+1; k++) {
                            built += ", " + parts[k].trim();
                        }
                        parts[i] = built;
                        int k = j;
                        while (k > i) {
                            parts[k] = "";
                            k--;
                        }
                    }
                    j++;
                }
            }
            i++;
        }
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                String[] hold = part.split(":");
                if (hold.length > 2) {
                    for (int ii = 2; ii < hold.length; ii++) {
                        hold[1] += ": " + hold[ii].trim();
                    }
                }
                String info = hold[1].trim();
                switch (classify(info)) {
                    case PRIMITIVE:
                        data.add(JsonPrimitive.parse(part));
                        break;
                    case STRING:
                        data.add(JsonString.parse(part));
                        break;
                    case ARRAY:
                        switch (classifyArray(info)) {
                            case PRIM_ARRAY:
                                data.add(JsonIntArray.parse(part));
                                break;
                            case STRING_ARRAY:
                                data.add(JsonStringArray.parse(part));
                                break;
                        }
                        break;
                    case OBJECT:
                        data.add(JsonObject.parse(part));
                        break;
                }
            }
        }
        return data;
    }

    /**
     * Utility function to classify a string of JSON
     *
     * @param data JSON to classify
     * @return JsonType describing the type of the given JSON
     */
    public static JsonType classify(String data) {
        if (data.startsWith("[") && data.endsWith("]")) {
            return JsonType.ARRAY;
        } else if (data.startsWith("{") && data.endsWith("}")) {
            return JsonType.OBJECT;
        } else {
            for (int i = 0; i < data.length(); i++) {
                if (!("0123456789".contains("" + data.charAt(i)))) {
                    return JsonType.STRING;
                }
            }
            return JsonType.PRIMITIVE;
        }
    }

    /**
     * Utility function to classify a string of JSON that has previously been identified as an array
     *
     * @param data JSON to classify
     * @return JsonType describing the type of array given
     */
    public static JsonType classifyArray(String data) {
        if (data.startsWith("[\"") && data.endsWith("\"]")) {
            return JsonType.STRING_ARRAY;
        }
        return JsonType.PRIM_ARRAY;
    }
}
