package com.gaoan.forever.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitProperties {

	private static final Logger logger = LoggerFactory.getLogger(InitProperties.class);

	private static Properties prop = new Properties();

	public static String readProperties(String key) {
		if (prop.size() == 0) {
			prop = initProperties();
		}

		String value = prop.getProperty(key);
		return value;
	}

	/**
	 * 读取外部properties文件内容
	 * 
	 * @param path
	 * @return
	 */
	public static Properties initProperties() {
		prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config/properties/config.properties");
			prop.load(input);
		} catch (Exception e) {
			logger.error("initProperties error.", e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		return prop;
	}
}
