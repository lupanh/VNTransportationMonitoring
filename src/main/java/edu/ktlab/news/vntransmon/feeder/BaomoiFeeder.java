package edu.ktlab.news.vntransmon.feeder;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import edu.ktlab.news.vntransmon.data.NewsRawDocument;
import edu.ktlab.news.vntransmon.fetcher.BaomoiFetcher;
import edu.ktlab.news.vntransmon.fetcher.URLFetcher;
import edu.ktlab.news.vntransmon.io.OutputWriter;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class BaomoiFeeder {
	String[] urls;
	OutputWriter<NewsRawDocument> writer;

	public BaomoiFeeder(String[] urls, OutputWriter<NewsRawDocument> writer) {
		this.urls = urls;
		this.writer = writer;
	}

	public void feed() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Integer
				.parseInt(PropertyLoader.getInstance().getProperties("NUM_THREAD_FEEDER")));
		for (String url : urls) {
			scheduler.scheduleAtFixedRate(new PageFeeder(url, writer), 0,
					Integer.parseInt(PropertyLoader.getInstance().getProperties("BAOMOI_REFRESH_TIME")), SECONDS);
		}
	}
}

class PageFeeder implements Runnable {
	String url;
	OutputWriter<NewsRawDocument> writer;

	public PageFeeder(String url, OutputWriter<NewsRawDocument> writer) {
		this.url = url;
		this.writer = writer;
	}

	public void run() {
		System.out.println("Feed " + url);
		try {
			URLFetcher fetcher = new URLFetcher(url);
			List<URL> links = fetcher.getFullLinks();
			if (links != null)
				for (URL link : links) {
					if (link.toString().matches("(?s)http://www.baomoi\\.com/[^/]*/\\d+/\\d+.epi")) {
						NewsRawDocument doc = BaomoiFetcher.fetch(getBaomoiID(link.toString()));
						if (doc != null)
							writer.write(doc);
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int getBaomoiID(String link) {
		return Integer.parseInt(link.substring(link.lastIndexOf("/") + 1, link.lastIndexOf(".")));
	}
}
