package edu.ktlab.news.vntransmon.nlp.ner;

import java.io.File;
import java.nio.charset.Charset;

import edu.ktlab.news.vntransmon.nlp.tools.VNTWSSingleton;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class MaxentNERRecognizerFileExample {
	static String file = "eventAnnnotation.txt";

	public static void main(String[] args) throws Exception {
		MaxentNERRecognizer nerFinder = new MaxentNERRecognizer("models/ner/htmldata.model",
				MaxentNERFactoryExample1.createFeatureGenerator());
		String[] sents = FileHelper.readFileAsLines(file);
		String output = "";
		for (String sent : sents) {
			String[] tokens = VNTWSSingleton.getInstance().tokenize(sent);
			output += nerFinder.recognize(tokens) + "\n";
		}
		FileHelper.writeToFile(output, new File("eventAnnotated.txt"), Charset.forName("UTF-8"));
	}
}
