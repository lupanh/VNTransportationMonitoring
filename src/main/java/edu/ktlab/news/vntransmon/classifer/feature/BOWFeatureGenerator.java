package edu.ktlab.news.vntransmon.classifer.feature;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class BOWFeatureGenerator implements FeatureGenerator<String[], String> {
	public ArrayList<String> extractFeatures(String[] tokens, String sentence) {
		ArrayList<String> featureCollector = new ArrayList<String>();

		for (String token : tokens) {
			if (token.length() == 0)
				continue;
			if (StringUtils.isAlphanumericSpace(token.replace("_", " ")))
				featureCollector.add(token);

		}
		return featureCollector;
	}
}
