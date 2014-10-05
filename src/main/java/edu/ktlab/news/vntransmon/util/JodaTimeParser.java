package edu.ktlab.news.vntransmon.util;

import java.util.TimeZone;

import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.DateTimeZone;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;

public class JodaTimeParser {
	public static String parseDate(String date) {
		String pattern = "dd/MM/yyyy HH:mm";
		DateTime dateTime = DateTime.parse(date, DateTimeFormat.forPattern(pattern)).withZone(
				DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+7")));
		return dateTime.toString();
	}
}
