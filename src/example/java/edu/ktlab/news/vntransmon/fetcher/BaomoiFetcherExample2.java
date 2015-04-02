package edu.ktlab.news.vntransmon.fetcher;

import edu.ktlab.news.vntransmon.data.NewsRawDocument;

public class BaomoiFetcherExample2 {
	public static void main(String[] args) throws Exception {
		int id = 14078232;
		NewsRawDocument doc = BaomoiFetcher.fetch(id);
		System.out.println(doc.printJson());
	}
}
