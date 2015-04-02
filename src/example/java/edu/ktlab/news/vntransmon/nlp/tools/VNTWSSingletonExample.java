package edu.ktlab.news.vntransmon.nlp.tools;

import java.io.File;
import java.nio.charset.Charset;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.nlp.tools.VNTWSSingleton;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class VNTWSSingletonExample {
	static Gson gson = new Gson();

	public static void main(String[] args) throws Exception {
		File file = new File("data/classifier/5-classes/dacbietnghiemtrong/atgt11358.json");
		NewsRawDocument document = gson.fromJson(FileHelper.readFileAsString(file, Charset.forName("UTF-8")), NewsRawDocument.class);		
		String[] tokens = VNTWSSingleton.getInstance().tokenize(document.getText(), true, true);
		System.out.println(tokens.length);
	}
}
