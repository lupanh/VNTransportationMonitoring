package edu.ktlab.news.vntransmon.util;


public class JodaTimeExample2 {
	public static void main(String[] args) {
		String date = "13/03/2011 02:22";		
		System.out.println(JodaTimeParser.parseDate(date));
	}
}
