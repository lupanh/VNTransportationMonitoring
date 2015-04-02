package edu.ktlab.news.vntransmon.evex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.classifer.FiveClassesClassifier;
import edu.ktlab.news.vntransmon.classifer.TwoClassesClassifier;
import edu.ktlab.news.vntransmon.data.TrAcEvent;
import edu.ktlab.news.vntransmon.db.ElasticSearchConnection;
import edu.ktlab.news.vntransmon.db.EventAccidentESFunction;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class EventExtractionAnalysisExample {
	static EventAccidentESFunction esfunction;
	static EventExtractor extractor;
	static TwoClassesClassifier layer1Classifier;
	static FiveClassesClassifier layer2Classifier;
	static Gson gson = new Gson();
	static String SERVER = PropertyLoader.getInstance().getProperties("ELASTIC_SERVER");
	static String SOURCE_INDEX = "baomoi_news_db";
	static String SOURCE_TYPE = "article";
	static String TARGET_INDEX = "event_accident_db";
	static String TARGET_TYPE = "event";
	static int BATCH_SIZE = 100;

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

		ArrayList<String> ids = new ArrayList<String>();
		while (true) {
			try {

				List<TrAcEvent> events = esfunction.queryAllEventFrom(SOURCE_INDEX, SOURCE_TYPE,
						BATCH_SIZE, 0);
				for (TrAcEvent event : events) {
					if (ids.contains(event.getId()))
						continue;
					String layer1Label = layer1Classifier
							.getLabel(layer1Classifier.classify(event));
					String layer2Label = layer2Classifier
							.getLabel(layer2Classifier.classify(event));
					if (layer1Label.equals("tainan")) {
						event = extractor.extractSummary(event, true);
						event.setCategory(layer1Label);
						event.setLevelEvent(layer2Label);
						if (!event.getWhereEvent().equals(""))
							es.createIndexResponse(TARGET_INDEX, TARGET_TYPE, event.getId(),
									event.printJson());
					}
					ids.add(event.getId());
				}
				if (ids.size() > BATCH_SIZE * 10)
					for (int i = 0; i < BATCH_SIZE * 5; i++)
						ids.remove(i);
				Thread.sleep(300000);
			} catch (Exception e) {
			}
		}
	}

	static String getToday() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		return dateFormat.format(today);
	}
}
