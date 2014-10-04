package edu.ktlab.news.vntransmon.db;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class BaomoiDBMoving {
	static ElasticSearchConnection es;
	static BaomoiESFunction esfunction;
	static int size = 1000;
	static String SOURCE_INDEX = "baomoi_db";
	static String TARGET_INDEX = "baomoi_news_db";

	public static void main(String[] args) {
		es = new ElasticSearchConnection(PropertyLoader.getInstance().getProperties(
				"ELASTIC_SERVER"));
		esfunction = new BaomoiESFunction(es.getClient());

		SearchResponse response = es.getClient().prepareSearch(SOURCE_INDEX).setTypes("article")
				.setQuery(QueryBuilders.matchAllQuery()).setSize(1).execute().actionGet();

		long numHits = response.getHits().getTotalHits();
		int numPages = (numHits == 0) ? 0 : (int) (numHits / size) + 1;
		for (int i = 0; i < numPages; i++) {
			List<NewsRawDocument> articles = esfunction.queryArticleByMatchAll(SOURCE_INDEX,
					"article", size, i * size);
			for (NewsRawDocument doc : articles) {
				es.createIndexResponse(TARGET_INDEX, "article", doc.getId(), doc.printJson());
				esfunction.deleteByQuery(SOURCE_INDEX, "article", "id:" + doc.getId());
			}
		}
		es.deleteIndex(SOURCE_INDEX);
	}

}
