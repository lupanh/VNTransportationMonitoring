package edu.ktlab.news.vntransmon.db;

import java.io.IOException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.api.GetGeocoding;
import edu.ktlab.news.vntransmon.bean.TrAcEvent;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class UpdateEventGeoExample {
	static EventAccidentESFunction esfunction;
	static Gson gson = new Gson();
	static String SERVER = PropertyLoader.getInstance().getProperties("ELASTIC_SERVER");
	static String TARGET_INDEX = "event_accident_db";
	static String TARGET_TYPE = "event";

	public static void main(String[] args) throws IOException {
		ElasticSearchConnection es = new ElasticSearchConnection(SERVER);
		esfunction = new EventAccidentESFunction(es.getClient());

		SearchResponse response = es.getClient().prepareSearch(TARGET_INDEX).setTypes(TARGET_TYPE)
				.setSize(2400).addSort("date", SortOrder.DESC)
				.setQuery(QueryBuilders.matchAllQuery())
				.setPostFilter(FilterBuilders.missingFilter("geolocEvent")).execute().actionGet();
		for (SearchHit hit : response.getHits().getHits()) {
			TrAcEvent event = gson.fromJson(hit.sourceAsString(), TrAcEvent.class);
			event.setGeoLocEvent(GetGeocoding.getGeoFirstResult(event.getWhereEvent()));
			es.createIndexResponse(TARGET_INDEX, TARGET_TYPE, event.getId(), event.printJson());
		}
		es.closeConnection();
	}

}
