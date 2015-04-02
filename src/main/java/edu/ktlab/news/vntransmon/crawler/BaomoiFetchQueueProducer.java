package edu.ktlab.news.vntransmon.crawler;

import java.util.concurrent.BlockingQueue;

public class BaomoiFetchQueueProducer extends Thread {
	protected BlockingQueue<Integer> queue = null;
	protected int startID;
	protected int endID;
	protected boolean order;

	public BaomoiFetchQueueProducer(BlockingQueue<Integer> queue, int startID, int endID,
			boolean order) {
		this.queue = queue;
		this.startID = startID;
		this.endID = endID;
		this.order = order;
	}

	public void run() {
		if (order)
			for (int i = endID; i >= 0; i--) {
				try {
					queue.put(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		else
			for (int i = startID; i < endID; i++) {
				try {
					queue.put(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
}
