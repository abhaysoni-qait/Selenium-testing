package com.webapp.automation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HandlePropFile {
	

	private static Properties config;
	private static String path = "config.properties";
	private static FileInputStream file;
	
	static {
		init();
	}

	private static void init() {
		config = new Properties();
		try {
			file = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println("Sorry Can not Find Property File");
		}
		try {
			config.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return config.getProperty(key);
	}
	
}
