package edu.ktlab.news.vntransmon.db;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;

import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class QueryESExample {
	public static void main(String[] args) {
		ElasticSearchConnection es = new ElasticSearchConnection(PropertyLoader.getInstance()
				.getProperties("ELASTIC_SERVER"));
		SearchResponse response = es.getClient().prepareSearch(PropertyLoader.getInstance()
				.getProperties("INDEX_NAME")).setTypes("article")
				.setQuery(QueryBuilders.simpleQueryString("\"tai nạn giao thông\"")).execute().actionGet();
		System.out.println(response.getHits().getTotalHits());
	}

}
