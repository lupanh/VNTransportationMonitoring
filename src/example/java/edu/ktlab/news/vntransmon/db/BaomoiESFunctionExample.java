package edu.ktlab.news.vntransmon.db;

import java.util.List;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;

public class BaomoiESFunctionExample {

	public static void main(String[] args) {
		ElasticSearchConnection es = new ElasticSearchConnection("23.92.53.181");
		BaomoiESFunction esfunction = new BaomoiESFunction(es.getClient());
		List<NewsRawDocument> articles = esfunction.queryAllArticleByString(
				"\"một vụ tai nạn giao thông nghiêm trọng tại\"", 10);
		for (NewsRawDocument article : articles)
			System.out.println(article.getTitle());
	}
}
