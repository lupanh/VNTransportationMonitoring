package edu.ktlab.news.vntransmon.nlp.tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.ktlab.news.vntransmon.util.FileHelper;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;

public class POSTaggerSingleton {
	static POSTaggerSingleton instance = null;
	static String model = "models/pos/vi-pos.model";
	static POSTagger tagger;
	static Set<String> rejectKeyword;
	
	protected POSTaggerSingleton() throws Exception {
		InputStream in = new FileInputStream(model);
		POSModel posModel = new POSModel(in);
		tagger = new POSTaggerME(posModel);
		rejectKeyword = FileHelper.readFileIntoSet("data/rejectKeyword.txt", true);
	}

	public static POSTaggerSingleton getInstance() throws Exception {
		if (instance == null) {
			instance = new POSTaggerSingleton();
		}
		return instance;
	}

	public String[] tag(String[] text) throws Exception {
		return tagger.tag(text);
	}

	public List<String> getKeywords(String[] text) throws Exception {
		List<String> keywords = new ArrayList<String>();
		String[] tags = tagger.tag(text);
		for (int i = 0; i < tags.length; i++)
			if (tags[i].startsWith("N") || tags[i].startsWith("V") || tags[i].startsWith("A"))
				keywords.add(text[i]);
		return keywords;
	}
	
	public List<String> getKeywords(String[] text, List<String> tagNames, boolean isLowercase) throws Exception {
		List<String> keywords = new ArrayList<String>();
		String[] tags = tagger.tag(text);
		for (int i = 0; i < tags.length; i++)
			if (tagNames.contains(tags[i])) {
				if (text[i].length() <= 3)
					continue;
				if (rejectKeyword.contains(text[i].toLowerCase().replace("_", " ")))
					continue;
				
				if (isLowercase)
					keywords.add(text[i].toLowerCase());
				else
					keywords.add(text[i]);
			}
									
		return keywords;
	}
}
