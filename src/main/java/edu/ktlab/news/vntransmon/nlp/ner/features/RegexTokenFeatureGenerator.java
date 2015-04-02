package edu.ktlab.news.vntransmon.nlp.ner.features;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class RegexTokenFeatureGenerator extends FeatureGeneratorAdapter {
	static final String TOKEN_CLASS_PREFIX = "regex";
	Pattern mPatterns[];
	String type = "";

	public RegexTokenFeatureGenerator(Pattern patterns[], String type) {
		if (patterns == null || patterns.length == 0) {
			throw new IllegalArgumentException("patterns must not be null or empty!");
		}

		this.mPatterns = patterns;
		this.type = type;
	}

	public RegexTokenFeatureGenerator(Pattern patterns[]) {
		if (patterns == null || patterns.length == 0) {
			throw new IllegalArgumentException("patterns must not be null or empty!");
		}

		this.mPatterns = patterns;
	}

	public RegexTokenFeatureGenerator(String regex) {
		Pattern pattern = Pattern.compile(regex);
		this.mPatterns = new Pattern[] { pattern };
	}

	public RegexTokenFeatureGenerator(String regex, String type) {
		Pattern pattern = Pattern.compile(regex);
		this.mPatterns = new Pattern[] { pattern };
		this.type = type;
	}

	public RegexTokenFeatureGenerator(String[] regexs, String type) {
		Pattern[] patterns = new Pattern[regexs.length];
		for (int i = 0; i < regexs.length; i++)
			patterns[i] = Pattern.compile(regexs[i]);
		this.mPatterns = patterns;
		this.type = type;
	}

	public RegexTokenFeatureGenerator(String file, String charset, String type) {
		this(FileHelper.readFileAsLines(new File(file), Charset.forName(charset)), type);
	}

	@Override
	public void createFeatures(List<String> features, String[] words, int index,
			String[] previousOutcomes) {
		String word = words[index].replace("_", " ");

		for (Pattern mPattern : mPatterns) {
			Matcher matcher = mPattern.matcher(word);
			if (matcher.find()) {
				features.add(TOKEN_CLASS_PREFIX + ":" + type);
				break;
			}
		}
	}

}
