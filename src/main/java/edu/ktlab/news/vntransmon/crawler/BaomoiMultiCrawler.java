package edu.ktlab.news.vntransmon.crawler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BaomoiMultiCrawler {
	static int numThreads = 4;
	static int sizePool = 100;
	static int startBaomoiID = 1011200;
	//static int endBaomoiID = 14909501;
	static int endBaomoiID = 1012200;
	static String outFolder = "data/baomoi";
	
	public static void main(String[] args) throws Exception {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(sizePool);
		
		URLFetchQueueProducer producer = new URLFetchQueueProducer(queue, startBaomoiID, endBaomoiID);
		URLFetchQueueConsumer[] consumers = new URLFetchQueueConsumer[numThreads];
		
		producer.start();
		Thread.sleep(1000);
		for (int i = 0; i < numThreads; i++) {
			consumers[i] = new URLFetchQueueConsumer(i, queue, outFolder);
			consumers[i].start();
		}

		producer.join();
		for (int i = 0; i < numThreads; i++) {
			try {
				consumers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
