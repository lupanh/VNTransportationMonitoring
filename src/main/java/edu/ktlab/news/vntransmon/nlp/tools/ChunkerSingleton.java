package edu.ktlab.news.vntransmon.nlp.tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;

public class ChunkerSingleton {
	static ChunkerSingleton instance = null;
	static String model = "models/chunk/vi-chunk.model";
	static ChunkerME chunker;

	protected ChunkerSingleton() throws Exception {
		InputStream in = new FileInputStream(model);
		ChunkerModel posModel = new ChunkerModel(in);
		chunker = new ChunkerME(posModel);
	}

	public static ChunkerSingleton getInstance() throws Exception {
		if (instance == null) {
			instance = new ChunkerSingleton();
		}
		return instance;
	}
	
	public String[] chunk(String[] text) throws Exception {
		return chunker.chunk(text, POSTaggerSingleton.getInstance().tag(text));
	}
	
	public String[] chunkSpan(String[] text) throws Exception {
		List<String> spans = new ArrayList<String>();
		String[] chunks = chunker.chunk(text, POSTaggerSingleton.getInstance().tag(text));
		for (int i = 0; i < chunks.length; i++) {
			String span = "";
			if (chunks[i].startsWith("B-")) {
				span = text[i];
				int j = i + 1;
				while (chunks[j].startsWith("I-")) {
					span += " " + text[j];
					j++;
				}
				spans.add(span + "//" + chunks[i].replace("B-", ""));
				if (j != i + 1)
					i = j;		
			}				
		}
		
		return spans.toArray(new String[spans.size()]);
	}
	
	public List<String> getSpanKeywords(String[] text, List<String> chunkNames, boolean isLowercase) throws Exception {
		List<String> spans = new ArrayList<String>();
		String[] chunks = chunker.chunk(text, POSTaggerSingleton.getInstance().tag(text));
		for (int i = 0; i < chunks.length; i++) {
			String span = "";
			if (chunks[i].startsWith("B-")) {
				span = text[i];
				int j = i + 1;
				
				if (j < chunks.length)					
					while (chunks[j].startsWith("I-")) {
						span += " " + text[j];
						j++;
						if (j >= chunks.length)
							break;
					}
				if (chunkNames.contains(chunks[i].replace("B-", ""))) {
					if (span.length() > 3)
						spans.add(span.replace("_", " ").toLowerCase());
				}					
					
				if (j != i + 1)
					i = j;		
			}				
		}
		
		return spans;
	}
	
	public List<String> getKeywords(String[] text, List<String> chunkNames, boolean isLowercase) throws Exception {
		List<String> keywords = new ArrayList<String>();
		String[] chunks = chunk(text);
		for (int i = 0; i < chunks.length; i++)
			if (chunkNames.contains(chunks[i])) {
				if (isLowercase)
					keywords.add(text[i].toLowerCase());
				else
					keywords.add(text[i]);
			}
									
		return keywords;
	}
}
