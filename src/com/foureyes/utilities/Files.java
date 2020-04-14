package com.foureyes.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * A util-class that help developers be more faster in coding with
 * <b>java.io.File</b>
 */
public class Files {

	/**
	 * Create a new file no matter it exists or not. If the file already exists,
	 * it'll be deleted and the new file will be created.
	 * 
	 * @param file : The file that will be created
	 * @return The created file
	 * @throws IOException : Error while trying to create a new file.
	 */
	public static File createFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
		return file;
	}

	/**
	 * Load a jar file outside this plugin as a library. We use this because this
	 * bridge plugin need a lot of external libraries such as : socket.io, etc.
	 * 
	 * @param file : The jar file that will be loaded as a library
	 * @return true if the progress is success, false in the other hand.
	 * @throws Exception : If there's an error while trying to load the jar file.
	 */
	public static boolean loadLibrary(File file) throws Exception {
		if (file.exists() == false) {
			throw new Exception("Cannot find the library !");
		}
		URL url = file.toURI().toURL();

		URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		method.setAccessible(true);
		method.invoke(classLoader, url);
		return true;
	}

}
