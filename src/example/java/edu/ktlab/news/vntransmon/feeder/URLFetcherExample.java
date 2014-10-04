package edu.ktlab.news.vntransmon.feeder;

import edu.ktlab.news.vntransmon.fetcher.URLFetcher;

public class URLFetcherExample {

	public static void main(String[] args) {
		String url = "http://www.baomoi.com/Home/TheGioi.epi";
		URLFetcher fetcher = new URLFetcher(url);
		System.out.println(fetcher.getSource());
	}

}
