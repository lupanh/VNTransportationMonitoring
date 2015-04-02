package edu.ktlab.news.vntransmon.classifer.feature;

import java.util.ArrayList;

public class NGramFeatureGenerator implements FeatureGenerator<String[], String> {
	int ngram = 3;

	public NGramFeatureGenerator(int ngram) {
		this.ngram = ngram;
	}

	public ArrayList<String> extractFeatures(String[] tokens, String sentence) {
		ArrayList<String> featureCollector = new ArrayList<String>();

		for (int i = 0; i <= tokens.length - ngram; i++) {
			String feature = "";
			for (int j = i; j < i + ngram - 1; j++) {
				feature += tokens[j] + "_";
			}
			feature += tokens[i + ngram - 1];
			featureCollector.add(feature);
		}
		return featureCollector;
	}
}
