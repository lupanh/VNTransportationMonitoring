package edu.ktlab.news.vntransmon.crawler;

import edu.ktlab.news.vntransmon.io.ElasticSearchOutputWriter;

public class BaomoiElasticSearchWriterExample {
	public static void main(String[] args) throws Exception {
		BaomoiMultiCrawler1 crawler = new BaomoiMultiCrawler1(new ElasticSearchOutputWriter(false));
		crawler.crawl();
	}
}
