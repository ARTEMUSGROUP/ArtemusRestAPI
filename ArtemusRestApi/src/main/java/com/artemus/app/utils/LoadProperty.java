package com.artemus.app.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperty {

	public static String getPropertyText(String keyText) {

		Properties prop = new Properties();
		InputStream input = null;
		String valueText = "";
		try {
			input = new FileInputStream("global.properties");
			prop.load(input);
			valueText = prop.getProperty(keyText);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return valueText;
	}
	
	public static Properties getProperty() {

		Properties prop = new Properties();
		InputStream input = null;
		String valueText = "";
		try {
			input = new FileInputStream("global.properties");
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	
}
