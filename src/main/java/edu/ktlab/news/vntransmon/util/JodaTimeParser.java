package edu.ktlab.news.vntransmon.util;

import java.util.TimeZone;

import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.DateTimeZone;
import org.elasticsearch.common.joda.time.LocalDateTime;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;

public class JodaTimeParser {
	public static String parseDate(String date) {
		String pattern = "dd/MM/yyyy HH:mm";
		try {
			LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormat.forPattern(pattern));
			DateTime dateTimeWithZone = dateTime.toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+7")));
			return dateTimeWithZone.toString();
		} catch (Exception e) {
			return "";
		}		
	}
}
