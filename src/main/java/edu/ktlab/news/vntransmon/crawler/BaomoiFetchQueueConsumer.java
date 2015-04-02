package edu.ktlab.news.vntransmon.crawler;

import java.util.concurrent.BlockingQueue;

import edu.ktlab.news.vntransmon.data.NewsRawDocument;
import edu.ktlab.news.vntransmon.fetcher.BaomoiFetcher;
import edu.ktlab.news.vntransmon.io.OutputWriter;

public class BaomoiFetchQueueConsumer extends Thread {
	int id;
	BlockingQueue<Integer> queue = null;
	OutputWriter<NewsRawDocument> writer;

	public BaomoiFetchQueueConsumer(int id, BlockingQueue<Integer> queue,
			OutputWriter<NewsRawDocument> writer) {
		this.id = id;
		this.queue = queue;
		this.writer = writer;
	}

	public void run() {
		int index = 0;
		long start = System.currentTimeMillis();
		while (!queue.isEmpty()) {
			try {
				int baomoiID = queue.take();
				NewsRawDocument doc = BaomoiFetcher.fetch(baomoiID);
				if (doc != null) {
					writer.write(doc);
				}
				index++;
				if (index % 100 == 0) {
					long current = System.currentTimeMillis();
					System.out.println("Thread" + id + ":\t" + index + "\t" + (current - start)
							+ "ms");
					start = current;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
