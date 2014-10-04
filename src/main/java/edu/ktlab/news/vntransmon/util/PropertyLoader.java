package edu.ktlab.news.vntransmon.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
	private static PropertyLoader INSTANCE;
	private static String propertyFile = "conf/crawler.properties";
	private static Properties properties;

	private PropertyLoader(String propertyFile) {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(propertyFile));
		} catch (IOException e) {
		}
	}

	private PropertyLoader() {
		this(propertyFile);
	}

	public static synchronized PropertyLoader getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PropertyLoader();
		}
		return INSTANCE;
	}

	public String getProperties(String key) {
		return properties.getProperty(key);
	}
}
