package edu.ktlab.news.vntransmon.nlp.tools;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceSplitterSingleton {
	static SentenceSplitterSingleton instance = null;
	static String model = "models/sentsplitter/vi-sd.model";
	static SentenceDetector splitter;

	protected SentenceSplitterSingleton() throws Exception {
		InputStream in = new FileInputStream(model);
		SentenceModel sentdetectModel = new SentenceModel(in);
		splitter = new SentenceDetectorME(sentdetectModel);
	}

	public static SentenceSplitterSingleton getInstance() throws Exception {
		if (instance == null) {
			instance = new SentenceSplitterSingleton();
		}
		return instance;
	}

	public String[] split(String text) throws Exception {
		return splitter.sentDetect(text);
	}

}
