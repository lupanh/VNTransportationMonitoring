package edu.ktlab.news.vntransmon.nlp.ner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.util.StringList;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.BigramNameFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
import opennlp.tools.util.featuregen.DictionaryFeatureGenerator;
import opennlp.tools.util.featuregen.OutcomePriorFeatureGenerator;
import opennlp.tools.util.featuregen.PreviousMapFeatureGenerator;
import opennlp.tools.util.featuregen.SentenceFeatureGenerator;
import opennlp.tools.util.featuregen.TokenClassFeatureGenerator;
import opennlp.tools.util.featuregen.TokenFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;
import edu.ktlab.news.vntransmon.nlp.ner.MaxentNERFactory;
import edu.ktlab.news.vntransmon.nlp.ner.features.NgramTokenFeatureGenerator;
import edu.ktlab.news.vntransmon.nlp.ner.features.RegexTokenFeatureGenerator;
import edu.ktlab.news.vntransmon.nlp.ner.features.VnWordAnalysisFeatureGenerator;
import edu.ktlab.news.vntransmon.nlp.tools.VNTWSSingleton;

public class MaxentNERFactoryExample1 {
	static Dictionary loadDictionary(String file) throws Exception {
		Dictionary dict = new Dictionary(false);
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),
				"UTF-8"));
		String line = new String();
		while ((line = in.readLine()) != null) {
			dict.put(new StringList(VNTWSSingleton.getInstance().tokenize(line)));
		}
		in.close();
		return dict;
	}

	static int windowSize = 1;
	public static AdaptiveFeatureGenerator createFeatureGenerator() throws Exception {
		AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
				new AdaptiveFeatureGenerator[] {
						new WindowFeatureGenerator(new VnWordAnalysisFeatureGenerator(), windowSize, windowSize),
						new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), windowSize, windowSize),
						new WindowFeatureGenerator(new TokenFeatureGenerator(true), windowSize, windowSize),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 2, 2), windowSize, windowSize),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 3, 3), windowSize, windowSize),
						new DictionaryFeatureGenerator("loc", loadDictionary("models/ner/dictionary/city.txt")),
						new DictionaryFeatureGenerator("time", loadDictionary("models/ner/dictionary/time.txt")),
						new DictionaryFeatureGenerator("loc", loadDictionary("models/ner/dictionary/location.txt")),
						new DictionaryFeatureGenerator("loc", loadDictionary("models/ner/dictionary/district.txt")),
						new DictionaryFeatureGenerator("prfLoc", loadDictionary("models/ner/dictionary/prefixLoc.txt")),
						new WindowFeatureGenerator(new RegexTokenFeatureGenerator("models/ner/rule/date.txt", "UTF-8", "date"), windowSize, windowSize),
						new WindowFeatureGenerator(new RegexTokenFeatureGenerator("models/ner/rule/number.txt", "UTF-8", "number"), windowSize, windowSize),
						new WindowFeatureGenerator(new RegexTokenFeatureGenerator("models/ner/rule/percent.txt", "UTF-8", "percent"), windowSize, windowSize),
						new BigramNameFeatureGenerator(), new OutcomePriorFeatureGenerator(),
						new PreviousMapFeatureGenerator(),
						new SentenceFeatureGenerator(true, false) });
		return featureGenerator;
	}

	public static void main(String[] args) throws Exception {
		MaxentNERFactory ner = new MaxentNERFactory(createFeatureGenerator());
		// ner.trainNER("data/vntrans.2.0.txt", "models/ner/vntrans.2.0.model", 100, 1);
		ner.evaluatebyExactMatching("data/vntrans.2.0.txt", "models/ner/vntrans.2.0.model");
	}
}
