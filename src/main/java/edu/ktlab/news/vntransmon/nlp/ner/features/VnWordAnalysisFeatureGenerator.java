package edu.ktlab.news.vntransmon.nlp.ner.features;

import java.util.List;

import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;
import opennlp.tools.util.featuregen.StringPattern;

public class VnWordAnalysisFeatureGenerator extends FeatureGeneratorAdapter {
	private static final String TOKEN_CLASS_PREFIX = "vwa";

	@Override
	public void createFeatures(List<String> features, String[] words, int index,
			String[] previousOutcomes) {
		String[] tokens = words[index].split("_");
		String word = words[index].replace("_", " ");
		String wordRemoveSpace = word.replace(" ", "");

		StringPattern strPattern = StringPattern.recognize(wordRemoveSpace);
		if (strPattern.isAllCapitalLetter())
			features.add(TOKEN_CLASS_PREFIX + ":ac");

		boolean isAllInitUp = false;
		for (String token : tokens) {
			strPattern = StringPattern.recognize(token);
			if (strPattern.isInitialCapitalLetter())
				isAllInitUp = true;
			else {
				isAllInitUp = false;
				break;
			}				
		}
		
		if (isAllInitUp) {
			features.add(TOKEN_CLASS_PREFIX + ":aic");
			for (String token : tokens)
				features.add(TOKEN_CLASS_PREFIX + ":token:" + token);
		}		
			
	}

}
