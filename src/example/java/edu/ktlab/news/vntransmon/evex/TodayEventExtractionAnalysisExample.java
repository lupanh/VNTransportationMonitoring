package edu.ktlab.news.vntransmon.evex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.bean.TrAcEvent;
import edu.ktlab.news.vntransmon.classifer.FiveClassesClassifier;
import edu.ktlab.news.vntransmon.classifer.TwoClassesClassifier;
import edu.ktlab.news.vntransmon.db.ElasticSearchConnection;
import edu.ktlab.news.vntransmon.db.EventAccidentESFunction;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class TodayEventExtractionAnalysisExample {
	static EventAccidentESFunction esfunction;
	static EventExtractor extractor;
	static Gson gson = new Gson();
	static String SERVER = PropertyLoader.getInstance().getProperties("ELASTIC_SERVER");
	static String SOURCE_INDEX = "baomoi_news_db";
	static String SOURCE_TYPE = "article";
	static String TARGET_INDEX = "event_accident_db";
	static String TARGET_TYPE = "event";
	static int BATCH_SIZE = 100;

	public static void main(String[] args) {
		try {
			TwoClassesClassifier layer1Classifier = new TwoClassesClassifier();
			FiveClassesClassifier layer2Classifier = new FiveClassesClassifier();
			extractor = new EventExtractor();

			ElasticSearchConnection es = new ElasticSearchConnection(SERVER);
			esfunction = new EventAccidentESFunction(es.getClient());

			List<TrAcEvent> events = esfunction.queryAllEventRangeDate(SOURCE_INDEX, SOURCE_TYPE,
					getToday(), getToday(), BATCH_SIZE);
			for (TrAcEvent event : events) {
				String layer1Label = layer1Classifier.getLabel(layer1Classifier.classify(event));
				String layer2Label = layer2Classifier.getLabel(layer2Classifier.classify(event));
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

	static String getToday() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		return dateFormat.format(today);
	}
}
