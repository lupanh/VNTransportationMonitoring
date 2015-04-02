package edu.ktlab.news.vntransmon.db;

import java.io.File;
import java.nio.charset.Charset;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.fetcher.BaomoiFetcher;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class IDRetrievalExample {
	static String file = "data/classifier/2-classes.txt";
	static String folder = "data/classifier/2-classes/";

	public static void main(String[] args) throws Exception {
		String[] lines = FileHelper.readFileAsLines(file);

		// ElasticSearchConnection es = new ElasticSearchConnection("23.92.53.181");
		// BaomoiESFunction esfunction = new BaomoiESFunction(es.getClient());

		for (String line : lines) {
			String[] segs = line.split("\t");
			NewsRawDocument article = BaomoiFetcher.fetch(Integer.parseInt(segs[0]));
			// NewsRawDocument article = esfunction.getByBaomoiId(segs[0]);
			if (article == null) {
				System.out.println(segs[0]);
				continue;
			}				
			
			String label;
			if (segs[1].equals("1"))
				label = "tainan";
			else
				label = "none";
			
			File outFolder = new File(folder + label);
			if (!outFolder.exists())
				outFolder.mkdirs();			
			FileHelper.writeToFile(article.printJson(), new File(folder + label + "/" + article.getId() + ".json"), Charset.forName("UTF-8"));
		}
	}

}
