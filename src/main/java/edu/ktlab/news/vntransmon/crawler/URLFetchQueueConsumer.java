package edu.ktlab.news.vntransmon.crawler;

import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.fetcher.BaomoiFetcher;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class URLFetchQueueConsumer extends Thread {
	protected int id;
	protected BlockingQueue<Integer> queue = null;
	protected String outFolder;

	public URLFetchQueueConsumer(int id, BlockingQueue<Integer> queue, String outFolder) {
		this.id = id;
		this.queue = queue;
		this.outFolder = outFolder;
	}

	public void run() {
		int index = 0;
		long start = System.currentTimeMillis();
		while (!queue.isEmpty()) {
			try {
				int idBaomoi = queue.take();
				NewsRawDocument doc = BaomoiFetcher.fetch(idBaomoi);
				if (doc != null) {
					FileHelper.writeToFile(doc.printJson(), new File(outFolder + "/" + doc.getId()
							+ ".json"), Charset.forName("UTF-8"));

					index++;
					if (index % 1000 == 0) {
						long current = System.currentTimeMillis();
						System.out.println("Thread" + id + ":\t" + index + "\t" + (current - start)
								+ "ms");
						start = current;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
