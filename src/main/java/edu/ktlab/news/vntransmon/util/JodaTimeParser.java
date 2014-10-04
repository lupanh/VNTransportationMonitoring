package edu.ktlab.news.vntransmon.util;

import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;

public class JodaTimeParser {
	public static String parseDate(String date) {
		String pattern = "dd/MM/yyyy HH:mm";
		DateTime dateTime = DateTime.parse(date, DateTimeFormat.forPattern(pattern));
		return dateTime.toString();
	}
}
