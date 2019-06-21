package uo.ri.conf;

import java.io.IOException;
import java.util.Properties;

public class Conf {

	private static final String CONF_FILE = "configuration.properties";

	private static Conf instance;
	private Properties properties;

	/**
	 * Cargar.
	 */
	private Conf() {
		properties = new Properties();
		try {
			properties.load(Conf.class.getClassLoader().getResourceAsStream(
					CONF_FILE));
		} catch (IOException e) {
			throw new RuntimeException("Properties file can not be loaded", e);
		}
	}

	/**
	 * Obtener valor del fichero .properties
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return getInstance().getProperty(key);
	}

	/**
	 * getProperty.
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null)
			throw new RuntimeException("Property not found in config file");
		return value;
	}

	private static Conf getInstance() {
		if (instance == null)
			instance = new Conf();
		return instance;
	}
}