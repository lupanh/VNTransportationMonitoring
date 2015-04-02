package edu.ktlab.news.vntransmon.evex;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;

import edu.ktlab.news.vntransmon.bean.TrAcEvent;
import edu.ktlab.news.vntransmon.classifer.FiveClassesClassifier;
import edu.ktlab.news.vntransmon.classifer.TwoClassesClassifier;
import edu.ktlab.news.vntransmon.db.ElasticSearchConnection;
import edu.ktlab.news.vntransmon.db.EventAccidentESFunction;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class EventExtractionDBWriterExample {
	static ElasticSearchConnection es;
	static String SERVER = PropertyLoader.getInstance().getProperties("ELASTIC_SERVER");
	static String SOURCE_INDEX = "baomoi_news_db";
	static String SOURCE_TYPE = "article";
	static String TARGET_INDEX = "event_accident_db";
	static String TARGET_TYPE = "event";
	static EventAccidentESFunction esfunction;
	static EventExtractor extractor;
	static TwoClassesClassifier layer1Classifier;
	static FiveClassesClassifier layer2Classifier;
	static int BATCH_SIZE = 100;
	static int FROM = 1785500;

	public static void main(String[] args) {
		try {
			layer1Classifier = new TwoClassesClassifier();
			layer2Classifier = new FiveClassesClassifier();
			extractor = new EventExtractor();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		ElasticSearchConnection es = new ElasticSearchConnection(SERVER);
		esfunction = new EventAccidentESFunction(es.getClient());
		// es.createArticleMapping(TARGET_INDEX, TARGET_TYPE);

		SearchResponse response = es.getClient().prepareSearch(SOURCE_INDEX).setTypes(SOURCE_TYPE)
				.setQuery(QueryBuilders.matchAllQuery()).setSize(1).setFrom(FROM).execute()
				.actionGet();

		long numHits = response.getHits().getTotalHits();
		int numPages = (numHits == 0) ? 0 : (int) (numHits / BATCH_SIZE) + 1;
		for (int i = 0; i < numPages; i++) {
			try {
				List<TrAcEvent> events = esfunction.queryAllEventFrom(SOURCE_INDEX, SOURCE_TYPE,
						BATCH_SIZE, i * BATCH_SIZE + FROM);
				for (TrAcEvent event : events) {
					String layer1Label = layer1Classifier
							.getLabel(layer1Classifier.classify(event));
					String layer2Label = layer2Classifier
							.getLabel(layer2Classifier.classify(event));
					if (layer1Label.equals("tainan")) {
						event = extractor.extractSummary(event, false);
						event.setCategory(layer1Label);
						event.setLevelEvent(layer2Label);
						if (!event.getWhereEvent().equals(""))
							es.createIndexResponse(TARGET_INDEX, TARGET_TYPE, event.getId(),
									event.printJson());
					}
				}
			} catch (Exception e) {
			}
		}

	}
}
