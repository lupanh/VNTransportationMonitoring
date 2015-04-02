package edu.ktlab.news.vntransmon.classifer;

import java.io.File;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class TwoClassesClassifierExample {
	static String fileModel = "models/2-classes.model";
	static String fileWordlist = "models/2-classes.wordlist";
	static TwoClassesClassifier classifier;
	static Gson gson = new Gson();

	public static void main(String[] args) throws Exception {
		classifier = new TwoClassesClassifier(fileModel, fileWordlist);
		// testTextClassifier();
		// testJsonClassifier();
		testFolderClassifier();
	}

	static void testTextClassifier() throws Exception {
		String sent = "TPO - Khoảng 21h ngày 24/3, trên đường Phạm Văn Đồng, Quốc lộ 14, đoạn qua thành phố Pleiku, tỉnh Gia Lai đã xảy ra vụ tai nạn giao thông khiến một thanh niên bị thương.";
		double score = classifier.classify(sent);
		System.out.println(classifier.getLabel(score));
	}

	static void testJsonClassifier() throws Exception {
		File file = new File("data/classifier/5-classes/itnghiemtrong/atgt429.json");
		NewsRawDocument document = new NewsRawDocument();
		document = gson.fromJson(FileHelper.readFileAsString(file), NewsRawDocument.class);
		double score = classifier.classify(document);
		System.out.println(classifier.getLabel(score));
	}
	
	static void testFolderClassifier() throws Exception {
		File folder = new File("data/classifier/2-classes/tainan");
		for (File file : folder.listFiles()) {
			NewsRawDocument document = new NewsRawDocument();
			String json = FileHelper.readFileAsString(file);
			document = gson.fromJson(json, NewsRawDocument.class);
			double score = classifier.classify(document);
			String label = classifier.getLabel(score);
			if (label.equals("none"))
				System.out.println(json);	
		}		
	}
}
