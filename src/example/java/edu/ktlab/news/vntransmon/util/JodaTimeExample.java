package edu.ktlab.news.vntransmon.util;

import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;

public class JodaTimeExample {

	public static void main(String[] args) {
		String pattern = "dd/MM/yyyy HH:mm";
		DateTime dateTime = DateTime.parse("04/10/2014 19:41", DateTimeFormat.forPattern(pattern));
		System.out.println(dateTime.toString());
	}
}
