package edu.ktlab.news.vntransmon.feeder;

import edu.ktlab.news.vntransmon.io.ElasticSearchOutputWriter;
import edu.ktlab.news.vntransmon.util.FileHelper;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class BaomoiFeederExample {
	static String feederFile = "conf/baomoi_feeder.txt";

	public static void main(String... args) {
		String[] urls = FileHelper.readFileAsLines(feederFile);
		BaomoiFeeder feeder = new BaomoiFeeder(urls, new ElasticSearchOutputWriter(
				Boolean.parseBoolean(PropertyLoader.getInstance().getProperties("FIRST_TIME_RUN"))));
		feeder.feed();
	}
}
