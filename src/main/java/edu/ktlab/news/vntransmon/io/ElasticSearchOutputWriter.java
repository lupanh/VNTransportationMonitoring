package edu.ktlab.news.vntransmon.io;

import edu.ktlab.news.vntransmon.data.NewsRawDocument;
import edu.ktlab.news.vntransmon.db.BaomoiESFunction;
import edu.ktlab.news.vntransmon.db.ElasticSearchConnection;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class ElasticSearchOutputWriter implements OutputWriter<NewsRawDocument> {
	ElasticSearchConnection es;
	BaomoiESFunction esfunction;

	public ElasticSearchOutputWriter(boolean firstrun) {
		es = new ElasticSearchConnection(PropertyLoader.getInstance().getProperties(
				"ELASTIC_SERVER"));
		esfunction = new BaomoiESFunction(es.getClient());
		if (firstrun)
			es.createArticleMapping(PropertyLoader.getInstance().getProperties("INDEX_NAME"),
					PropertyLoader.getInstance().getProperties("TYPE_NEWS_NAME"));
	}

	public void write(NewsRawDocument doc) {
		es.createIndexResponse(PropertyLoader.getInstance().getProperties("INDEX_NAME"),
				PropertyLoader.getInstance().getProperties("TYPE_NEWS_NAME"), doc.getId(),
				doc.printJson());
	}
}
