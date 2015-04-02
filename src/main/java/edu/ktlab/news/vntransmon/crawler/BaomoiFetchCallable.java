package edu.ktlab.news.vntransmon.crawler;

import java.util.concurrent.Callable;

import edu.ktlab.news.vntransmon.data.NewsRawDocument;
import edu.ktlab.news.vntransmon.fetcher.BaomoiFetcher;

public class BaomoiFetchCallable implements Callable<NewsRawDocument>{
	int baomoiID;

	public BaomoiFetchCallable(int baomoiID) {
		this.baomoiID = baomoiID;
	}
	
	public NewsRawDocument call() throws Exception {
		NewsRawDocument doc = BaomoiFetcher.fetch(baomoiID);		
		return doc;
	}

}
