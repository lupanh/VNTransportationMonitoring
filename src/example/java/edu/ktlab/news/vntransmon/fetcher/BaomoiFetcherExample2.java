package edu.ktlab.news.vntransmon.fetcher;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;

public class BaomoiFetcherExample2 {
	public static void main(String[] args) throws Exception {
		int id = 14909136;
		NewsRawDocument doc = BaomoiFetcher.fetch(id);
		System.out.println(doc.printJson());
	}
}
