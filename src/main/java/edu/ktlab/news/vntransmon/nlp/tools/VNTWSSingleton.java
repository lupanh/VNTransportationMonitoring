package edu.ktlab.news.vntransmon.nlp.tools;

import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import vn.hus.nlp.tokenizer.Tokenizer;
import vn.hus.nlp.tokenizer.TokenizerOptions;
import vn.hus.nlp.tokenizer.TokenizerProvider;
import vn.hus.nlp.tokenizer.tokens.TaggedWord;

public class VNTWSSingleton {
	static VNTWSSingleton instance = null;
	static String propertiesFilename = "models/vn.properties";
	static Properties properties = new Properties();	
	static Tokenizer tokenizer;
	
	protected VNTWSSingleton() throws Exception {
		properties.load(new FileInputStream(propertiesFilename));
		tokenizer = TokenizerProvider.getInstance(propertiesFilename).getTokenizer();
	}

	public static VNTWSSingleton getInstance() throws Exception {
		if (instance == null) {
			instance = new VNTWSSingleton();
		}
		return instance;
	}
	
	public String segment(String text) throws Exception {
		String[] tokens = tokenize(text);
		return StringUtils.join(tokens, " ");
	}
	
	public String[] tokenize(String text) throws Exception {
		ArrayList<String> tokens = new ArrayList<String>();
		StringReader reader = new StringReader(text);
		tokenizer.tokenize(reader);
		List<TaggedWord> list = tokenizer.getResult();
		for (TaggedWord taggedWord : list) {
			String word = taggedWord.toString();
			if (TokenizerOptions.USE_UNDERSCORE) {
				word = word.replaceAll("\\s+", "_");
			} else {
				word = "[" + word + "]";
			}
			tokens.add(word);			
		}
		return tokens.toArray(new String[tokens.size()]);
	}
	
	public String[] tokenize(String text, boolean sentsplit, boolean sensitive) throws Exception {
		if (sensitive)
			text = text.toLowerCase();
		
		if (sentsplit) {
			ArrayList<String> tokens = new ArrayList<String>();
			String[] paragraphs = text.split("\n");
			for (String paragraph : paragraphs) {
				if (paragraph.length() < 2)
					continue;				
				String[] sents = SentenceSplitterSingleton.getInstance().split(paragraph);			
				for (String sent : sents) {
					for (String token : tokenize(sent))
						tokens.add(token);
				}	
			}		
				
			return tokens.toArray(new String[tokens.size()]);
		} else {
			return tokenize(text);
		}			
	}
	
	public static void main(String...strings) throws Exception {
		String text = "Trương Thị May hạnh phúc diện kiến Đức Pháp Vương Drukpa";
		System.out.println(VNTWSSingleton.getInstance().segment(text));
	}

}
