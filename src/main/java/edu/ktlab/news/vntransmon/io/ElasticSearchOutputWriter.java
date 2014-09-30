package edu.ktlab.news.vntransmon.io;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.db.ElasticSearchConnection;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class ElasticSearchOutputWriter implements OutputWriter<NewsRawDocument> {
	ElasticSearchConnection es = new ElasticSearchConnection(PropertyLoader.getInstance()
			.getProperties("ELASTIC_SERVER"));

	public void write(NewsRawDocument doc) {
		es.createIndexResponse(PropertyLoader.getInstance().getProperties("INDEX_NAME"),
				PropertyLoader.getInstance().getProperties("TYPE_NEWS_NAME"), doc.printJson());
	}
}
