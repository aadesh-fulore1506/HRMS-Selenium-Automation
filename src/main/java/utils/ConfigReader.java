package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	private static final Properties props = new Properties();

	static {
		try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (in == null) {
				throw new RuntimeException("config.properties not found. Put it in src/test/resources.");
			}
			props.load(in);
		} catch (IOException e) {
			throw new RuntimeException("Could not load config.properties", e);
		}
	}

	private ConfigReader() {
	}

	public static String get(String key) {
		String value = System.getProperty(key, props.getProperty(key));
		if (value == null) {
			throw new RuntimeException("Missing config key: " + key);
		}
		return value.trim();
	}

	public static int getInt(String key) {
		return Integer.parseInt(get(key));
	}

	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(get(key));
	}
}