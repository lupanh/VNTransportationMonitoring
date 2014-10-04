package edu.ktlab.news.vntransmon.db;

public class DeleteByQueryExample {

	public static void main(String[] args) {
		ElasticSearchConnection es = new ElasticSearchConnection("localhost");
		BaomoiESFunction esfunction = new BaomoiESFunction(es.getClient());
		esfunction.deleteByQuery("baomoi_news_db", "article", "id:14964293");
	}

}
