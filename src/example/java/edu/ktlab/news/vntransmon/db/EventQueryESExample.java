package edu.ktlab.news.vntransmon.db;

import java.io.IOException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

public class EventQueryESExample {
	public static void main(String[] args) throws IOException {
		ElasticSearchConnection es = new ElasticSearchConnection("23.92.53.181");
		SearchResponse response = es.getClient().prepareSearch("event_accident_db")
				.setTypes("event").setSize(10).addSort("date", SortOrder.DESC)
				.setQuery(QueryBuilders.matchAllQuery()).setPostFilter(
				FilterBuilders.missingFilter("geolocEvent"))
				.execute().actionGet();
		for (SearchHit hit : response.getHits().getHits()) {
			System.out.println(hit.getSourceAsString());
		}
		es.closeConnection();
	}
}
