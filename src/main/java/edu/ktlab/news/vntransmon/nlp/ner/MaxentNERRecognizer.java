package edu.ktlab.news.vntransmon.nlp.ner;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import edu.ktlab.news.vntransmon.nlp.tagger.TextSpan;

public class MaxentNERRecognizer {
	AdaptiveFeatureGenerator featureGenerator;
	NameFinderME nerFinder;
	int beamSize = 3;

	public MaxentNERRecognizer(String modelPath, AdaptiveFeatureGenerator featureGenerator)
			throws Exception {
		this.featureGenerator = featureGenerator;
		loadNERModel(modelPath);
	}

	void loadNERModel(String modelPath) throws Exception {
		InputStream model = new FileInputStream(modelPath);
		loadNERModel(model);
	}

	void loadNERModel(InputStream model) throws Exception {
		TokenNameFinderModel nerModel = new TokenNameFinderModel(model);
		nerFinder = new NameFinderME(nerModel, featureGenerator, beamSize);
	}

	public String recognize(String[] tokens) {
		Span[] spans = nerFinder.find(tokens);
		return TextSpan.getStringNameSample(spans, tokens);
	}
	
	public Span[] find(String[] tokens) {
		return nerFinder.find(tokens);
	}
}
