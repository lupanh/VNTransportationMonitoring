package edu.ktlab.news.vntransmon.crawler;

import java.util.concurrent.BlockingQueue;

public class BaomoiFetchQueueProducer extends Thread {
	protected BlockingQueue<Integer> queue = null;
	protected int startID;
	protected int endID;

	public BaomoiFetchQueueProducer(BlockingQueue<Integer> queue, int startID, int endID) {
		this.queue = queue;
		this.startID = startID;
		this.endID = endID;
	}

	public void run() {
		for (int i = startID; i <= endID; i++) {
			try {
				queue.put(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
