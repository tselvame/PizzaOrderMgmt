package com.pizza.order.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class OrderUtil {
	
	public static Timestamp getGMTTimestamp() {
		
		SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		localDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String xmtDt = localDateFormat.format(new Date());
		Timestamp ts = Timestamp.valueOf(xmtDt);
		
		return ts;
		
	}

}
