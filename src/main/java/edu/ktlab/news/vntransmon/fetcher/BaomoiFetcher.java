package edu.ktlab.news.vntransmon.fetcher;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;

public class BaomoiFetcher {
	public static NewsRawDocument fetch(int idBaomoi) {
		String url = "http://www.baomoi.com/a-b-c/126/" + idBaomoi + ".epi";
		Document doc = fetchURL(url);
		if (doc == null)
			return null;
		
		NewsRawDocument newsdoc = new NewsRawDocument();
		newsdoc.setId(idBaomoi + "");
		newsdoc.setUrl(doc.getElementsByTag("fb:comments").attr("href"));
		newsdoc.setTitle(StringEscapeUtils.unescapeHtml4(doc.select("h1.title").text()));
		newsdoc.setDate(StringEscapeUtils.unescapeHtml4(doc.select("span.time").text()));
		newsdoc.setSummary(StringEscapeUtils.unescapeHtml4(doc.select("div.story-body h2.summary")
				.text()));
		newsdoc.setContent(StringEscapeUtils.unescapeHtml4(doc.select("div[itemprop=articleBody]")
				.text()));

		return newsdoc;
	}

	public static Document fetchURL(String url) {
		Document doc;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla").cookie("auth", "token").timeout(3000)
					.get();
		} catch (Exception e) {
			return null;
		}
		return doc;
	}

	public static String getRootURL(String redirectURL) {
		Document doc = fetchURL(redirectURL);
		if (doc != null)
			return doc.baseUri();
		else
			return null;
	}
}
