package edu.ktlab.news.vntransmon.evex;

import java.io.File;
import java.nio.charset.Charset;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.classifer.FiveClassesClassifier;
import edu.ktlab.news.vntransmon.classifer.TwoClassesClassifier;
import edu.ktlab.news.vntransmon.data.TrAcEvent;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class EventExtractorExample {
	static String fileOutput = "test/event20000.txt";
	static Gson gson = new Gson();

	public static void main(String[] args) {
		try {
			TwoClassesClassifier layer1Classifier = new TwoClassesClassifier();
			FiveClassesClassifier layer2Classifier = new FiveClassesClassifier();
			EventExtractor extractor = new EventExtractor();

			String[] jsons = FileHelper.readFileAsLines("data/data.20000.txt");
			for (String json : jsons) {
				TrAcEvent doc = new TrAcEvent();
				doc = gson.fromJson(json, TrAcEvent.class);

				String layer1Label = layer1Classifier.getLabel(layer1Classifier.classify(doc));
				String layer2Label = layer2Classifier.getLabel(layer2Classifier.classify(doc));
				if (layer1Label.equals("tainan")) {
					doc = extractor.extractSummary(json, false);
					doc.setCategory(layer1Label);
					doc.setLevelEvent(layer2Label);
					if (!doc.getWhereEvent().equals(""))
						FileHelper.appendToFile(doc.printJson() + "\n", new File(fileOutput),
								Charset.forName("UTF-8"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
