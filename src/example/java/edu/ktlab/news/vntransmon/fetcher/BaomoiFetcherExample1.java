package edu.ktlab.news.vntransmon.fetcher;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.ktlab.news.vntransmon.fetcher.BaomoiFetcher;

public class BaomoiFetcherExample1 {
	public static void main(String[] args) throws Exception {
		String url = "http://www.baomoi.com/a-b-c/126/14909136.epi";
		Document doc = Jsoup.connect(url).userAgent("Mozilla").cookie("auth", "token")
				.timeout(3000).get();
		System.out.println(BaomoiFetcher.getRootURL(doc.getElementsByTag("fb:comments").attr("href")));
		System.out.println(StringEscapeUtils.unescapeHtml4(doc.select("h1.title").text()));
		System.out.println(StringEscapeUtils.unescapeHtml4(doc.select("span.time").text()));
		System.out.println(StringEscapeUtils.unescapeHtml4(doc.select("div.story-body h2.summary").text()));
		System.out.println(StringEscapeUtils.unescapeHtml4(doc.select("div[itemprop=articleBody]").text()));		
	}
}
