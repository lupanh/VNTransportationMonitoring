package edu.ktlab.news.vntransmon.nlp.ner.features;

import java.util.ArrayList;
import java.util.List;

import edu.ktlab.news.vntransmon.nlp.ner.features.VnWordAnalysisFeatureGenerator;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;

public class VnWordAnalysisFeatureGeneratorExample {
	public static void main(String[] args) {
		AdaptiveFeatureGenerator windowFeatureGenerator = new WindowFeatureGenerator(
				new VnWordAnalysisFeatureGenerator(), 2, 2);
		String[] testSentence = new String[] { "anh", "Vũ", "yêu", "chị", "Lê_Hoàng_Quỳnh" };

		for (int i = 0; i < testSentence.length; i++) {
			List<String> features = new ArrayList<String>();

			windowFeatureGenerator.createFeatures(features, testSentence, i, null);
			System.out.println("============================");
			System.out.println("word:" + testSentence[i]);
			for (String feature : features)
				System.out.println(feature);
		}

	}
}
