package edu.ktlab.news.vntransmon.crawler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import edu.ktlab.news.vntransmon.data.NewsRawDocument;
import edu.ktlab.news.vntransmon.io.OutputWriter;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class BaomoiMultiCrawler1 implements Crawler {
	int NUM_THREAD = Integer.parseInt(PropertyLoader.getInstance().getProperties("NUM_THREAD_FETCHER"));
	int SIZE_POOL = Integer.parseInt(PropertyLoader.getInstance().getProperties("SIZE_POOL"));
	int BAOMOI_STARTID = Integer.parseInt(PropertyLoader.getInstance().getProperties("BAOMOI_STARTID"));
	int BAOMOI_ENDID = Integer.parseInt(PropertyLoader.getInstance().getProperties("BAOMOI_ENDID"));
	OutputWriter<NewsRawDocument> writer;

	public BaomoiMultiCrawler1(OutputWriter<NewsRawDocument> writer) {
		this.writer = writer;
	}

	public BaomoiMultiCrawler1(int numthread, int sizepool, int startid, int endid,
			OutputWriter<NewsRawDocument> writer) {
		this.NUM_THREAD = numthread;
		this.SIZE_POOL = sizepool;
		this.BAOMOI_STARTID = startid;
		this.BAOMOI_ENDID = endid;
		this.writer = writer;
	}

	public void crawl() throws Exception {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(SIZE_POOL);

		BaomoiFetchQueueProducer producer = new BaomoiFetchQueueProducer(queue, BAOMOI_STARTID,	BAOMOI_ENDID);
		BaomoiFetchQueueConsumer[] consumers = new BaomoiFetchQueueConsumer[NUM_THREAD];

		producer.start();
		Thread.sleep(1000);
		for (int i = 0; i < NUM_THREAD; i++) {
			consumers[i] = new BaomoiFetchQueueConsumer(i, queue, writer);
			consumers[i].start();
		}

		producer.join();
		for (int i = 0; i < NUM_THREAD; i++) {
			try {
				consumers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
