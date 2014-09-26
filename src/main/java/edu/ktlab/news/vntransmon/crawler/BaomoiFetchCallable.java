package edu.ktlab.news.vntransmon.crawler;

import java.util.concurrent.Callable;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.fetcher.BaomoiFetcher;

public class BaomoiFetchCallable implements Callable<NewsRawDocument>{
	int baomoiID;
	String outFolder;

	public BaomoiFetchCallable(int baomoiID, String outFolder) {
		this.baomoiID = baomoiID;
		this.outFolder = outFolder;
	}
	
	public NewsRawDocument call() throws Exception {
		NewsRawDocument doc = BaomoiFetcher.fetch(baomoiID);		
		return doc;
	}

}
