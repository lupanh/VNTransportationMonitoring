package edu.ktlab.news.vntransmon.db;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import edu.ktlab.news.vntransmon.util.FileHelper;
import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class BaomoiQueryESExample {
	public static void main(String[] args) throws IOException {
		ElasticSearchConnection es = new ElasticSearchConnection("23.92.53.181");
		SearchResponse response = es.getClient()
				.prepareSearch(PropertyLoader.getInstance().getProperties("INDEX_NAME"))
				.setTypes("article").setSize(20000).addSort("date", SortOrder.DESC)
				.setQuery(QueryBuilders.simpleQueryStringQuery("\"tai nạn giao thông\"")).execute()
				.actionGet();
		for (SearchHit hit : response.getHits().getHits()) {
			System.out.println(hit.getId());
			FileHelper.appendToFile(hit.getSourceAsString() + "\n", new File("data/data.20000.txt"),
					Charset.forName("UTF-8"));
		}
		es.closeConnection();
	}
}
