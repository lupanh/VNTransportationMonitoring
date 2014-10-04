package edu.ktlab.news.vntransmon.db;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class QueryESExample {
	public static void main(String[] args) {
		ElasticSearchConnection es = new ElasticSearchConnection("23.92.53.181");
		SearchResponse response = es.getClient().prepareSearch(PropertyLoader.getInstance()
				.getProperties("INDEX_NAME")).setTypes("article")
				.setQuery(QueryBuilders.simpleQueryString("\"tai nạn giao thông\"")).execute().actionGet();
		for (SearchHit hit : response.getHits().getHits()) {
			System.out.println(hit.getSourceAsString());
		}
	}
}
