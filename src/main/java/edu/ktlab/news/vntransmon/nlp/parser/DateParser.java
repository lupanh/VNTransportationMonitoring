package edu.ktlab.news.vntransmon.nlp.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateParser {
	private static DateFormat[] formats = new DateFormat[] {
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US),
			new SimpleDateFormat("yyyy-MM-dd", Locale.US),
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
			new SimpleDateFormat("E, MMM dd, yyyy HH:mm", Locale.US),
			new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US),
			new SimpleDateFormat("dd/MM/yyyy", Locale.US),
			new SimpleDateFormat("dd/MM", Locale.US), new SimpleDateFormat("dd.MM", Locale.US),
			new SimpleDateFormat("dd-MM", Locale.US) };

	public static String[] date = { "sáng sớm", "chiều tối", "nửa đêm", "rạng sáng", "gần sáng",
			"đêm", "khuya", "hôm", "ngày", "sáng", "trưa", "chiều", "tối", "sớm", "giữa trưa" };

	public static Date parser(String text) {
		text = text.toLowerCase();
		for (String prefix : date)
			if (text.contains(prefix))
				text = text.replaceAll(prefix, "").trim();
		for (DateFormat format : formats) {
			try {
				Date date = format.parse(text);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				if (cal.get(Calendar.YEAR) <= 1970)
					cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
				return cal.getTime();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public static Date getNow() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static String formatDate(Date date) {
		if (date == null)
			return null;
		String strDate = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		strDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
				+ cal.get(Calendar.YEAR);
		return strDate;
	}

	public static Date updateDate(Date date, int dateUpdate) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);

		cal.add(Calendar.DATE, dateUpdate);
		return cal.getTime();
	}

	public static void main(String[] args) throws Exception {
		String text = "Wed, 28 Mar 2012 19:33:25 +0900";
		Date date = DateParser.parser(text);
		System.out.println(DateParser.formatDate(date));
		// System.out.println(DateParser.updateDate(date,-30));
	}
}
