package com.falconsocial.redis.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class RedisConfigProvider {
	final static String REDIS_STORE_PROPERTIES_FILE = "resources/redis";
	final static String REDIS_HOST_KEY = "redis.host";
	final static String REDIS_PORT_KEY = "redis.port";
	final static String REDIS_JSON_CHANNEL_KEY = "json.channel";
	final static String REDIS_JSON_MESSAGE_KEY_KEY = "json.message.key";

	static String hostName = "localhost";
	static int portNumber = 6379;
	static String jsonChannelName = "json-channel";
	static String jsonMessageKey = "json-message";

	public RedisConfigProvider() {
		ResourceBundle dataStoreConfig = getDataStoreConfigFile();
		if (dataStoreConfig != null) {
			hostName = dataStoreConfig.getString(REDIS_HOST_KEY);
			portNumber = Integer.parseInt(dataStoreConfig.getString(REDIS_PORT_KEY));
			jsonChannelName = dataStoreConfig.getString(REDIS_JSON_CHANNEL_KEY);
			jsonMessageKey = dataStoreConfig.getString(REDIS_JSON_MESSAGE_KEY_KEY);
		}
	}

	ResourceBundle getDataStoreConfigFile() {
		try {
			return ResourceBundle.getBundle(REDIS_STORE_PROPERTIES_FILE);
		} catch (MissingResourceException missingResourceException) {
			System.out.println("Properties file " + REDIS_STORE_PROPERTIES_FILE + " not found. Using default values.");
		}
		return null;
	}

	public static String getHostName() {
		return hostName;
	}

	public static void setHostName(String hostName) {
		RedisConfigProvider.hostName = hostName;
	}

	public static int getPortNumber() {
		return portNumber;
	}

	public static void setPortNumber(int portNumber) {
		RedisConfigProvider.portNumber = portNumber;
	}

	public static String getJsonChannelName() {
		return jsonChannelName;
	}

	public static void setJsonChannelName(String jsonChannelName) {
		RedisConfigProvider.jsonChannelName = jsonChannelName;
	}

	public static String getJsonMessageKey() {
		return jsonMessageKey;
	}

	public static void setJsonMessageKey(String jsonMessageKey) {
		RedisConfigProvider.jsonMessageKey = jsonMessageKey;
	}

}
