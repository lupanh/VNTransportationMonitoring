package edu.ktlab.news.vntransmon.db;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.data.TrAcEvent;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class EventAccidentESFunction {
	Client client;
	Gson gson = new Gson();

	public EventAccidentESFunction(Client client) {
		this.client = client;
	}

	public boolean checkExistEventId(String indexname, String id) {
		try {
			SearchResponse response = client.prepareSearch(indexname)
					.setQuery(QueryBuilders.termQuery("id", id)).execute().actionGet();
			if (response.getHits().getTotalHits() > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public TrAcEvent getByEventId(String indexname, String id) {
		SearchResponse response = client.prepareSearch(indexname)
				.setQuery(QueryBuilders.termQuery("id", id)).execute().actionGet();
		if (response.getHits().getTotalHits() == 1) {
			SearchHit hit = response.getHits().getHits()[0];
			TrAcEvent event = gson.fromJson(hit.sourceAsString(), TrAcEvent.class);
			return event;
		} else
			return null;
	}

	public TrAcEvent getByEventId(String id) {
		SearchResponse response = client
				.prepareSearch(PropertyLoader.getInstance().getProperties("INDEX_NAME"))
				.setQuery(QueryBuilders.termQuery("id", id)).execute().actionGet();
		if (response.getHits().getTotalHits() == 1) {
			SearchHit hit = response.getHits().getHits()[0];
			TrAcEvent event = gson.fromJson(hit.sourceAsString(), TrAcEvent.class);
			return event;
		} else
			return null;
	}

	public List<TrAcEvent> queryEventByString(String query, int size, int from) {
		return queryEventByString(PropertyLoader.getInstance().getProperties("INDEX_NAME"),
				PropertyLoader.getInstance().getProperties("TYPE_NEWS_NAME"), query, size, from);
	}

	public List<TrAcEvent> queryEventByString(String index, String type, String query, int size,
			int from) {
		List<TrAcEvent> events = new ArrayList<TrAcEvent>();

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.simpleQueryStringQuery(query)).setSize(size).setFrom(from)
				.execute().actionGet();
		System.err.println("Query:" + query + " (Number of hits "
				+ response.getHits().getTotalHits() + ", size " + size + ", from " + from + ")");

		for (SearchHit hit : response.getHits().getHits()) {
			TrAcEvent article = gson.fromJson(hit.sourceAsString(), TrAcEvent.class);
			events.add(article);
		}

		return events;
	}

	public List<TrAcEvent> queryAllEventFrom(String index, String type, int size, int from) {
		List<TrAcEvent> events = new ArrayList<TrAcEvent>();

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.matchAllQuery()).addSort("date", SortOrder.DESC)
				.setSize(size).setFrom(from).execute().actionGet();
		System.err.println("Query:*:* (Number of hits " + response.getHits().getTotalHits()
				+ ", size " + size + ", from " + from + ")");

		for (SearchHit hit : response.getHits().getHits()) {
			TrAcEvent event = gson.fromJson(hit.sourceAsString(), TrAcEvent.class);
			events.add(event);
		}

		return events;
	}

	public List<TrAcEvent> queryAllEventByString(String query, int size) {
		return queryAllEventByString(PropertyLoader.getInstance().getProperties("INDEX_NAME"),
				PropertyLoader.getInstance().getProperties("TYPE_NEWS_NAME"), query, size);
	}

	public List<TrAcEvent> queryAllEventByString(String index, String type, String query, int size) {
		List<TrAcEvent> articles = new ArrayList<TrAcEvent>();

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.simpleQueryStringQuery(query)).setSize(1).execute()
				.actionGet();

		long numHits = response.getHits().getTotalHits();
		int numPages = (numHits == 0) ? 0 : (int) (numHits / size) + 1;
		for (int i = 0; i < numPages; i++)
			articles.addAll(queryEventByString(index, type, query, size, i * size));

		return articles;
	}

	public List<TrAcEvent> queryAllEvent(String index, String type, int size) {
		List<TrAcEvent> events = new ArrayList<TrAcEvent>();

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.matchAllQuery()).setSize(1).execute().actionGet();

		long numHits = response.getHits().getTotalHits();
		int numPages = (numHits == 0) ? 0 : (int) (numHits / size) + 1;
		for (int i = 0; i < numPages; i++)
			events.addAll(queryAllEventFrom(index, type, size, i * size));

		return events;
	}

	public List<TrAcEvent> queryAllEventRangeDate(String index, String type, String dateFrom,
			String dateTo, int size, int from) {
		List<TrAcEvent> events = new ArrayList<TrAcEvent>();

		SearchResponse response = client
				.prepareSearch(index)
				.setTypes(type)
				.setQuery(QueryBuilders.matchAllQuery())
				.addSort("date", SortOrder.DESC)
				.setPostFilter(
						FilterBuilders.queryFilter(QueryBuilders.rangeQuery("date").from(dateFrom)
								.to(dateTo).timeZone("+7:00"))).setSize(size).setFrom(from)
				.execute().actionGet();

		System.err.println("Query:*:* (Date: " + dateFrom + ") (Number of hits "
				+ response.getHits().getTotalHits() + ", size " + size + ", from " + from + ")");

		for (SearchHit hit : response.getHits().getHits()) {
			TrAcEvent event = gson.fromJson(hit.sourceAsString(), TrAcEvent.class);
			events.add(event);
		}

		return events;
	}

	public List<TrAcEvent> queryAllEventRangeDate(String index, String type, String dateFrom,
			String dateTo, int size) {
		List<TrAcEvent> events = new ArrayList<TrAcEvent>();

		SearchResponse response = client
				.prepareSearch(index)
				.setTypes(type)
				.setQuery(QueryBuilders.matchAllQuery())
				.setPostFilter(
						FilterBuilders.queryFilter(QueryBuilders.rangeQuery("date").from(dateFrom)
								.to(dateTo).timeZone("+7:00"))).setSize(1).execute().actionGet();

		long numHits = response.getHits().getTotalHits();
		int numPages = (numHits == 0) ? 0 : (int) (numHits / size) + 1;
		for (int i = 0; i < numPages; i++)
			events.addAll(queryAllEventRangeDate(index, type, dateFrom, dateTo, size, i * size));

		return events;
	}

	public void deleteById(String index, String type, String id) {
		client.prepareDelete(index, type, id).execute().actionGet();
	}

	public void deleteByQuery(String index, String type, String query) {
		client.prepareDeleteByQuery(index).setQuery(QueryBuilders.simpleQueryStringQuery(query))
				.execute().actionGet();
	}
}
