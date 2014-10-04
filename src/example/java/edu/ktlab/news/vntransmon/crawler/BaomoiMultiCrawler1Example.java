package edu.ktlab.news.vntransmon.crawler;

import edu.ktlab.news.vntransmon.io.JsonFileOutputWriter;

public class BaomoiMultiCrawler1Example {
	static String outFolder = "data/baomoi";

	public static void main(String[] args) throws Exception {
		BaomoiMultiCrawler1 crawler = new BaomoiMultiCrawler1(new JsonFileOutputWriter(outFolder));
		crawler.crawl();
	}
}
