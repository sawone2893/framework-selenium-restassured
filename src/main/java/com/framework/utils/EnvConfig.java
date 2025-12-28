package com.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvConfig {

	private static Properties properties;

	public static void init() {
		// Read from command line
		String env = System.getProperty("env"); 

		// Default to 'qa' if no environment is specified
		if (env == null) {
			env = "qa";
		}

		String filePath = "src/test/resources/envConfig/config-" + env + ".properties";

		try {
			FileInputStream fis = new FileInputStream(filePath);
			properties = new Properties();
			properties.load(fis);
		} catch (IOException e) {
			throw new RuntimeException("Could not load config file for environment: " + env);
		}
	}

	public static String get(String key) {
		if (properties == null)
			init();
		return properties.getProperty(key);
	}

	public static String getOrDefault(String key, String def) {
		return properties.getProperty(key, def);
	}

	public static boolean getBool(String key) {
		return Boolean.parseBoolean(properties.getProperty(key, "false"));
	}
	
	public static int getInt(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}

}
