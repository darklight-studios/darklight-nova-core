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

/**
 * Base class of all JSON data
 */
public abstract class JsonData {
    private String name;

    /**
     * Serialize this object into JSON
     * @return JSON representation of this object
     */
    public abstract String jsonify();

    /**
     * @param name the name of the data
     */
    public JsonData(String name) {
        this.name = name;
    }

    /**
     * Get the name of the data
     * @return the name of the data
     */
    public String getName() {
        return name;
    }
}
