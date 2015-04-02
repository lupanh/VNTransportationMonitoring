package edu.ktlab.news.vntransmon.io;

import java.io.File;
import java.nio.charset.Charset;

import edu.ktlab.news.vntransmon.data.NewsRawDocument;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class JsonFileOutputWriter implements OutputWriter<NewsRawDocument> {
	String outFolder;

	public JsonFileOutputWriter(String outFolder) {
		this.outFolder = outFolder;
	}

	public void write(NewsRawDocument doc) {
		try {
			FileHelper.writeToFile(doc.printJson(), new File(outFolder + "/" + doc.getId()
					+ ".json"), Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
