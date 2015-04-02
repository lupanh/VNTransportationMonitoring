package edu.ktlab.news.vntransmon.db;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class BaomoiESFunction {
	Client client;
	Gson gson = new Gson();

	public BaomoiESFunction(Client client) {
		this.client = client;
	}

	public boolean checkExistBaomoiId(String indexname, String id) {
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

	public NewsRawDocument getByBaomoiId(String indexname, String id) {
		SearchResponse response = client.prepareSearch(indexname)
				.setQuery(QueryBuilders.termQuery("id", id)).execute().actionGet();
		if (response.getHits().getTotalHits() == 1) {
			SearchHit hit = response.getHits().getHits()[0];
			NewsRawDocument article = gson.fromJson(hit.sourceAsString(), NewsRawDocument.class);
			return article;
		} else
			return null;
	}
	
	public NewsRawDocument getByBaomoiId(String id) {
		SearchResponse response = client.prepareSearch(PropertyLoader.getInstance().getProperties("INDEX_NAME"))
				.setQuery(QueryBuilders.termQuery("id", id)).execute().actionGet();
		if (response.getHits().getTotalHits() == 1) {
			SearchHit hit = response.getHits().getHits()[0];
			NewsRawDocument article = gson.fromJson(hit.sourceAsString(), NewsRawDocument.class);
			return article;
		} else
			return null;
	}

	public List<NewsRawDocument> queryArticleByString(String query, int size, int from) {
		return queryArticleByString(PropertyLoader.getInstance().getProperties("INDEX_NAME"),
				PropertyLoader.getInstance().getProperties("TYPE_NEWS_NAME"), query, size, from);
	}

	public List<NewsRawDocument> queryArticleByString(String index, String type, String query,
			int size, int from) {
		List<NewsRawDocument> articles = new ArrayList<NewsRawDocument>();

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.simpleQueryStringQuery(query)).setSize(size).setFrom(from)
				.execute().actionGet();
		System.err.println("Query:" + query + " (Number of hits "
				+ response.getHits().getTotalHits() + ", size " + size + ", from " + from + ")");

		for (SearchHit hit : response.getHits().getHits()) {
			NewsRawDocument article = gson.fromJson(hit.sourceAsString(), NewsRawDocument.class);
			articles.add(article);
		}

		return articles;
	}

	public List<NewsRawDocument> queryAllArticle(String index, String type, int size,
			int from) {
		List<NewsRawDocument> articles = new ArrayList<NewsRawDocument>();

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.matchAllQuery()).setSize(size).setFrom(from).execute()
				.actionGet();
		System.err.println("Query:*:* (Number of hits " + response.getHits().getTotalHits()
				+ ", size " + size + ", from " + from + ")");

		for (SearchHit hit : response.getHits().getHits()) {
			NewsRawDocument article = gson.fromJson(hit.sourceAsString(), NewsRawDocument.class);
			articles.add(article);
		}

		return articles;
	}

	public List<NewsRawDocument> queryAllArticleByString(String query, int size) {
		return queryAllArticleByString(PropertyLoader.getInstance().getProperties("INDEX_NAME"),
				PropertyLoader.getInstance().getProperties("TYPE_NEWS_NAME"), query, size);
	}

	public List<NewsRawDocument> queryAllArticleByString(String index, String type, String query,
			int size) {
		List<NewsRawDocument> articles = new ArrayList<NewsRawDocument>();

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.simpleQueryStringQuery(query)).setSize(1).execute().actionGet();

		long numHits = response.getHits().getTotalHits();
		int numPages = (numHits == 0) ? 0 : (int) (numHits / size) + 1;
		for (int i = 0; i < numPages; i++)
			articles.addAll(queryArticleByString(index, type, query, size, i * size));

		return articles;
	}

	public List<NewsRawDocument> queryAllArticle(String index, String type, int size) {
		List<NewsRawDocument> articles = new ArrayList<NewsRawDocument>();

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(QueryBuilders.matchAllQuery()).setSize(1).execute().actionGet();

		long numHits = response.getHits().getTotalHits();
		int numPages = (numHits == 0) ? 0 : (int) (numHits / size) + 1;
		for (int i = 0; i < numPages; i++)
			articles.addAll(queryAllArticle(index, type, size, i * size));

		return articles;
	}

	public void deleteById(String index, String type, String id) {
		client.prepareDelete(index, type, id).execute().actionGet();
	}

	public void deleteByQuery(String index, String type, String query) {
		client.prepareDeleteByQuery(index).setQuery(QueryBuilders.simpleQueryStringQuery(query))
				.execute().actionGet();
	}
}
