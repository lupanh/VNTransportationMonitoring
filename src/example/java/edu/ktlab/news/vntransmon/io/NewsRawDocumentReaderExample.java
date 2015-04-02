package edu.ktlab.news.vntransmon.io;

import java.io.File;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.nlp.tools.SentenceSplitterSingleton;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class NewsRawDocumentReaderExample {
	static String folder = "data/classifier/2-classes/tainan";
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		for (File file : new File(folder).listFiles()) {
			NewsRawDocument document = new NewsRawDocument();
			document = gson.fromJson(FileHelper.readFileAsString(file), NewsRawDocument.class);
			String[] sents = SentenceSplitterSingleton.getInstance().split(document.getSummary());
			for (String sent : sents)
				System.out.println(sent);
		}
	}

}
