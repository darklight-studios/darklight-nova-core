package com.ijg.darklight.sdk.loader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

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

public class DarklightLoader {
	
	/**
	 * Load a class out of a jar file
	 * @param classToLoad Name of the class to load out of the jar
	 * @param jarPath The path to the jar file
	 * @return The loaded class (uninstantiated)
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public static Class<?> loadClassFromJar(String classToLoad, String jarPath) throws MalformedURLException, ClassNotFoundException {
		ClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new URL("jar:file:/" + jarPath + "!/") }, DarklightLoader.class.getClassLoader());
		Class<?> loadedClass = Class.forName(classToLoad, true, classLoader);
		return loadedClass;
	}
	
	/**
	 * Load and instantiate a class from a jar file
	 * @param classToLoad Name of the class to load out of the jar
	 * @param jarPath The path to the jar file
	 * @return A new instance of the class
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public static Object loadAndInstantiateJar(String classToLoad, String jarPath) throws InstantiationException, IllegalAccessException, MalformedURLException, ClassNotFoundException {
		Class<?> loadedClass;
		loadedClass = loadClassFromJar(classToLoad, jarPath);
		return loadedClass.newInstance();
	}
	
	/**
	 * Load and instantiate a class form a jar file with constructor arguments
	 * @param classToLoad Name of the class to load out of the jar
	 * @param jarPath The path of the jar file
	 * @param constructorArgs Arguments for the class constructor
	 * @param constructorArgTypes The argument types for the constructor
	 * @return A new instance of the class
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object loadAndInstantiateJar(String classToLoad, String jarPath, Object[] constructorArgs, Class<?>... constructorArgTypes) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> loadedClass = loadClassFromJar(classToLoad, jarPath);
		Constructor<?> classConstructor = loadedClass.getConstructor(constructorArgTypes);
		return classConstructor.newInstance(constructorArgs);
	}
	
	/**
	 * Load a java class file
	 * @param classToLoad Name of the class to load
	 * @return The loaded class (uninstantiated)
	 * @throws ClassNotFoundException
	 */
	public static Class<?> loadClass(String classToLoad) throws ClassNotFoundException {
		ClassLoader classLoader = DarklightLoader.class.getClassLoader();
		Class<?> loadedClass = classLoader.loadClass(classToLoad);
		return loadedClass;
	}
	
	/**
	 * Load and instantiate a java class file with a constructor
	 * @param classToLoad Name of the class to load
	 * @param constructorArgs Arguments for the class constructor
	 * @param constructorArgTypes The argument types for the constructor
	 * @return A new instance of the class
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object loadAndInstantiateClass(String classToLoad, Object[] constructorArgs, Class<?>... constructorArgTypes) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> loadedClass = loadClass(classToLoad);
		Constructor<?> classConstructor = loadedClass.getConstructor(constructorArgTypes);
		return classConstructor.newInstance(constructorArgs);
	}
	
	/**
	 * Load and instantiate a java class file with no constructor
	 * @param classToLoad Name of the class to load
	 * @return A new instance of the class
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object loadAndInstantiateClass(String classToLoad) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> loadedClass = loadClass(classToLoad);
		return loadedClass.newInstance();
	}
}
