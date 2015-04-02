package edu.ktlab.news.vntransmon.classifer.feature;

import java.util.ArrayList;

public class ContextGenerator {
	private FeatureGenerator<String[], String>[] mFeatureGenerators;

	public ContextGenerator(FeatureGenerator<String[], String>[] mFeatureGenerators) {
		this.mFeatureGenerators = mFeatureGenerators;
	}

	public ArrayList<String> getContext(String[] tokens, String sentence) {
		ArrayList<String> context = new ArrayList<String>();
		for (FeatureGenerator<String[], String> generator : mFeatureGenerators) {
			ArrayList<String> extractedFeatures = generator.extractFeatures(tokens, sentence);
			context.addAll(extractedFeatures);
		}
		return context;
	}
}
