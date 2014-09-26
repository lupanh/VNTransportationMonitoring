package edu.ktlab.news.vntransmon.crawler;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class BaomoiMultiCrawler2 {
	static int numThreads = 4;
	static int sizePool = 100;
	static int timeout = 5000;
	static int startBaomoiID = 1011200;
	// static int endBaomoiID = 14909501;
	static int endBaomoiID = 1012200;
	static String outFolder = "data/baomoi";

	public static void main(String[] args) throws Exception {
		int runner = 0;
		List<Integer> ids = new ArrayList<Integer>();

		long startTime = System.currentTimeMillis();

		for (int i = startBaomoiID; i <= endBaomoiID; i++) {
			runner++;
			ids.add(i);
			if (runner % sizePool == 0) {
				excuteList(ids, runner, startTime);
				ids = new ArrayList<Integer>();
			}
		}
		excuteList(ids, runner, startTime);
	}

	static void excuteList(List<Integer> ids, int runner, long startTime) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		List<Future<NewsRawDocument>> list = new ArrayList<Future<NewsRawDocument>>();
		for (int id : ids) {
			Callable<NewsRawDocument> worker = new BaomoiFetchCallable(id, outFolder);
			Future<NewsRawDocument> collector = executor.submit(worker);
			list.add(collector);
		}
		for (Future<NewsRawDocument> future : list) {
			try {
				NewsRawDocument doc = future.get(timeout, TimeUnit.MILLISECONDS);
				if (doc != null) {
					FileHelper.writeToFile(doc.printJson(), new File(outFolder + "/" + doc.getId()
							+ ".json"), Charset.forName("UTF-8"));
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
