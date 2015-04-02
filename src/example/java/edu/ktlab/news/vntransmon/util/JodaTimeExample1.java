package edu.ktlab.news.vntransmon.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.DateTimeZone;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;

public class JodaTimeExample1 {

	public static void main(String[] args) {
		String pattern = "dd/MM/yyyy HH:mm";
		DateTime dateTime = DateTime.parse("13/03/2011 02:22", DateTimeFormat.forPattern(pattern))
				.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+7")));
		System.out.println(dateTime.toString());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		System.out.println(dateFormat.format(today));
	}
}
