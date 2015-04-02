package edu.ktlab.news.vntransmon.db;

import java.io.IOException;

import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.google.gson.Gson;

public class QueryJsonESExample {
	static EventAccidentESFunction esfunction;
	static Gson gson = new Gson();
	static String SERVER = "23.92.53.181";
	static String TARGET_INDEX = "event_accident_db";
	static String TARGET_TYPE = "event";

	public static void main(String[] args) throws IOException {
		ElasticSearchConnection es = new ElasticSearchConnection(SERVER);
		esfunction = new EventAccidentESFunction(es.getClient());

		System.out.println(es
				.getClient()
				.prepareSearch(TARGET_INDEX)
				.setTypes(TARGET_TYPE)
				.setQuery(QueryBuilders.matchAllQuery())
				.addSort("date", SortOrder.DESC)
				.setPostFilter(
						FilterBuilders.queryFilter(QueryBuilders.rangeQuery("date")
								.from("2015-03-08").to("2015-03-10").timeZone("+7:00")))
				.setSize(10));
		es.closeConnection();
	}

}
