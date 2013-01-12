package com.ijg.darklight.sdk.loader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class DarklightLoader {
	
	public static Class<?> loadClassFromJar(String classToLoad, String jarPath) throws MalformedURLException, ClassNotFoundException {
		ClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new URL("jar:file:/" + jarPath + "!/") }, DarklightLoader.class.getClassLoader());
		Class<?> loadedClass = Class.forName(classToLoad, true, classLoader);
		return loadedClass;
	}
	
	public static Object loadAndInstantiateJar(String classToLoad, String jarPath) throws InstantiationException, IllegalAccessException, MalformedURLException, ClassNotFoundException {
		Class<?> loadedClass;
		loadedClass = loadClassFromJar(classToLoad, jarPath);
		return loadedClass.newInstance();
	}
	
	public static Class<?> loadClass(String classToLoad) throws ClassNotFoundException {
		ClassLoader classLoader = DarklightLoader.class.getClassLoader();
		Class<?> loadedClass = classLoader.loadClass(classToLoad);
		return loadedClass;
	}
	
	public static Object loadAndInstantiateClass(String classToLoad, Object[] constructorArgs, Class<?>... constructorArgTypes) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> loadedClass = loadClass(classToLoad);
		Constructor<?> classConstructor = loadedClass.getConstructor(constructorArgTypes);
		return classConstructor.newInstance(constructorArgs);
	}
	
	public static Object loadAndInstantiateClass(String classToLoad) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> loadedClass = loadClass(classToLoad);
		return loadedClass.newInstance();
	}
}
