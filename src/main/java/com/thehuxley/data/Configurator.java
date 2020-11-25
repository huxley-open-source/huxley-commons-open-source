package com.thehuxley.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Configurator {

	private static Map<String, Properties> properties = new HashMap<String, Properties>();
	static Logger logger = LoggerFactory.getLogger(Configurator.class);

	private Configurator() {
	}

	public static Properties getProperties(String fileName) {
		Properties prop = properties.get(fileName);
		if (prop == null) {
			if (logger.isInfoEnabled()) {
				logger.info("Loading configuration file: " + fileName);
			}
			prop = new Properties();
			try {
				if (System.getenv("HUXLEY_SERVICE_HOME") != null) {
					prop.load(new FileInputStream(System
							.getenv("HUXLEY_SERVICE_HOME")
							+ System.getProperty("file.separator") + fileName));
					properties.put(fileName, prop);
				} else {
					InputStream file = Configurator.class.getClassLoader()
							.getResourceAsStream(fileName);
					if (file == null) {
						logger.error("Cannot find ["
								+ fileName
								+ "] in the classpath. You must indicate the full path from the classpath root. "
								+ "Take a look at: http://stackoverflow.com/questions/1464291/how-to-really-read-text-file-from-classpath-in-java");
						prop = null;
					} else {
						prop.load(file);
						properties.put(fileName, prop);
					}
				}
			} catch (FileNotFoundException e) {
				logger.error("Cannot read file. Returning null: "
						+ e.getMessage());
				prop = null;
			} catch (IOException e) {
				logger.error("Cannot read file. Returning null: ", e);
				prop = null;
			}
		}

		return prop;
	}

	public static String getProperty(String fileName, String key) {
		return getProperties(fileName).getProperty(key);
	}

}
