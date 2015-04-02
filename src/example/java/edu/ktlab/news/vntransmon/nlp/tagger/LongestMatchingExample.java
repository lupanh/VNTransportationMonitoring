package edu.ktlab.news.vntransmon.nlp.tagger;

import java.util.HashMap;

import opennlp.tools.tokenize.SimpleTokenizer;

public class LongestMatchingExample {
	public static HashMap<String, String> dict = new HashMap<String, String>();

	public static void main(String[] args) {
		dict.put("thành phố", "prefix");
		dict.put("hà nội", "city");
		
		String sentence = "thành phố Hà Nội. thủ đô Hà Nội 19/02/2015";		
		String tokens[] = SimpleTokenizer.INSTANCE.tokenize(sentence);
		LongestMatching matching = new LongestMatching(dict);
		TextSpan[] spans = matching.tagging(tokens, -1, true);
		for (TextSpan span : spans) {
			System.out.println(TextSpan.spanToString(span, tokens) + "\t" + span.getType());
		}
		System.out.println(TextSpan.getStringAnnotated(spans, tokens));
	}

}
