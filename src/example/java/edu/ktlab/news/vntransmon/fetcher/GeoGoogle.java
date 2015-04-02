package edu.ktlab.news.vntransmon.fetcher;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GeoGoogle {
	static String key = "SRM4VEFQFHIXxjDpJPU0Fg2Oaw6G9TZg";

	public static String[] getCoordinates(String address) {
		try {
			String addr = address;
			addr = addr.replace(' ', '+');
			URL u = new URL("https://maps.google.com/maps/geo?q=\'" + addr + "\'&output=xml&key="
					+ key);

			Document doc = Jsoup.connect(u.toString()).userAgent("Mozilla").cookie("auth", "token")
					.timeout(3000).get();
			String content = doc.html();
			if (content.equals(""))
				return null;

			Pattern regex = Pattern.compile("<coordinates>([^<]*)");
			Matcher regexMatcher = regex.matcher(content);
			String[] str = null;
			while (regexMatcher.find()) {
				str = regexMatcher.group(1).trim().split(",");
			}
			return str;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String... arg) throws Exception {
		System.out.println(GeoGoogle.getCoordinates("Hai Bà Trưng,đường Trần Nhân Tông,Hà Nội")[0]);
	}
}