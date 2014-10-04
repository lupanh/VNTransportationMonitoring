package edu.ktlab.news.vntransmon.feeder;

import java.net.URL;
import java.util.List;

import edu.ktlab.news.vntransmon.fetcher.URLFetcher;

public class BaomoiIDGrabberExample {
	public static void main(String[] args) throws Exception {
		String url = "http://www.baomoi.com/Home/TheGioi.epi";
		URLFetcher fetcher = new URLFetcher(url);
		List<URL> links = fetcher.getFullLinks();
		if (links != null)
			for (URL link : links) {
				if (link.toString().matches("(?s)http://www.baomoi\\.com/[^/]*/\\d+/\\d+.epi"))
					System.out.println(getBaomoiID(link.toString()) + "\t" + link);
			}
	}

	static int getBaomoiID(String link) {
		return Integer.parseInt(link.substring(link.lastIndexOf("/") + 1, link.lastIndexOf(".")));
	}
}
