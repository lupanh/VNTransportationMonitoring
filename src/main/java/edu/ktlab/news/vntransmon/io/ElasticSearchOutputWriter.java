package edu.ktlab.news.vntransmon.io;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.db.BaomoiESFunction;
import edu.ktlab.news.vntransmon.db.ElasticSearchConnection;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class ElasticSearchOutputWriter implements OutputWriter<NewsRawDocument> {
	ElasticSearchConnection es = new ElasticSearchConnection(PropertyLoader.getInstance()
			.getProperties("ELASTIC_SERVER"));
	BaomoiESFunction esfunction = new BaomoiESFunction(es.getClient());

	public synchronized void write(NewsRawDocument doc) {		
		if (!esfunction.checkExistBaomoiId(PropertyLoader.getInstance().getProperties("INDEX_NAME"), doc.getId()))
			es.createIndexResponse(PropertyLoader.getInstance().getProperties("INDEX_NAME"),
					PropertyLoader.getInstance().getProperties("TYPE_NEWS_NAME"), doc.printJson());
	}
}
