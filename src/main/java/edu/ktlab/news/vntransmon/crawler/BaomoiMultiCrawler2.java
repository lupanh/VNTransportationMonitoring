package edu.ktlab.news.vntransmon.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.io.OutputWriter;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class BaomoiMultiCrawler2 implements Crawler {
	int NUM_THREAD = Integer.parseInt(PropertyLoader.getInstance().getProperties("NUM_THREAD_FETCHER"));
	int SIZE_POOL = Integer.parseInt(PropertyLoader.getInstance().getProperties("SIZE_POOL"));
	int BAOMOI_STARTID = Integer.parseInt(PropertyLoader.getInstance().getProperties("BAOMOI_STARTID"));
	int BAOMOI_ENDID = Integer.parseInt(PropertyLoader.getInstance().getProperties("BAOMOI_ENDID"));
	int TIMEOUT = Integer.parseInt(PropertyLoader.getInstance().getProperties("FETCHER_TIMEOUT"));
	OutputWriter<NewsRawDocument> writer;

	public BaomoiMultiCrawler2(OutputWriter<NewsRawDocument> writer) {
		this.writer = writer;
	}

	public BaomoiMultiCrawler2(int numthread, int sizepool, int startid, int endid,
			OutputWriter<NewsRawDocument> writer) {
		this.NUM_THREAD = numthread;
		this.SIZE_POOL = sizepool;
		this.BAOMOI_STARTID = startid;
		this.BAOMOI_ENDID = endid;
		this.writer = writer;
	}

	public void crawl() throws Exception {
		int runner = 0;
		List<Integer> ids = new ArrayList<Integer>();

		long startTime = System.currentTimeMillis();

		for (int i = BAOMOI_STARTID; i <= BAOMOI_ENDID; i++) {
			runner++;
			ids.add(i);
			if (runner % SIZE_POOL == 0) {
				excuteList(ids, runner, startTime, writer);
				ids = new ArrayList<Integer>();
			}
		}
		excuteList(ids, runner, startTime, writer);
	}

	void excuteList(List<Integer> ids, int runner, long startTime,
			OutputWriter<NewsRawDocument> writer) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);
		List<Future<NewsRawDocument>> list = new ArrayList<Future<NewsRawDocument>>();
		for (int id : ids) {
			Callable<NewsRawDocument> worker = new BaomoiFetchCallable(id);
			Future<NewsRawDocument> collector = executor.submit(worker);
			list.add(collector);
		}
		for (Future<NewsRawDocument> future : list) {
			try {
				NewsRawDocument doc = future.get(TIMEOUT, TimeUnit.MILLISECONDS);
				if (doc != null) {
					writer.write(doc);
				}
			} catch (TimeoutException e) {
				future.cancel(true);
			}
		}
		long currentTime = System.currentTimeMillis();
		System.out.println("Fetcher " + runner + "\t" + (currentTime - startTime) + "ms");
		startTime = currentTime;
		executor.shutdown();
	}
}
