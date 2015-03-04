package com.falconsocial.redis.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataProvider {
	static String ID_FORMAT = "yyyyMMddHHmmssSSSS";
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(ID_FORMAT);
	
	public static String getId() {
		Date now = new Date();
		return dateFormatter.format(now);
	}
}
