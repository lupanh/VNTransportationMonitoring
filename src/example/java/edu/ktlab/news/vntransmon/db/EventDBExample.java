package edu.ktlab.news.vntransmon.db;

import edu.ktlab.news.vntransmon.bean.TrAcEvent;

public class EventDBExample {	
	static ElasticSearchConnection es;
	static String SERVER = "23.92.53.181";
	static String TARGET_INDEX = "event_accident_db";
	static String TARGET_TYPE = "event";
	static EventAccidentESFunction esfunction;

	public static void main(String[] args) {
		es = new ElasticSearchConnection(SERVER);
		esfunction = new EventAccidentESFunction(es.getClient());
		es.createArticleMapping(TARGET_INDEX, TARGET_TYPE);

		TrAcEvent event = new TrAcEvent();
		event.setTitle("tai nan xay ra tai Hanoi");
		event.setGeoLocEvent("20.868475,106.302234");
		for (int i = 0; i < 10; i++)
			es.createIndexResponse(TARGET_INDEX, TARGET_TYPE, i + "", event.printJson());
	}

}
