package edu.ktlab.news.vntransmon.crawler;

import edu.ktlab.news.vntransmon.io.JsonFileOutputWriter;

public class BaomoiMultiCrawler2Example {
	public static void main(String[] args) throws Exception {
		String outFolder = "data/baomoi";
		BaomoiMultiCrawler2 crawler = new BaomoiMultiCrawler2(new JsonFileOutputWriter(outFolder));
		crawler.crawl();
	}
}
