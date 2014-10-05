package edu.ktlab.news.vntransmon.fetcher;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.nodes.Document;

import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.util.JodaTimeParser;

public class BaomoiFetcher {
	public static NewsRawDocument fetch(int idBaomoi) {
		String url = "http://www.baomoi.com/a-b-c/126/" + idBaomoi + ".epi";
		Document doc = URLFetcher.fetch(url);
		if (doc == null)
			return null;
		
		NewsRawDocument newsdoc = new NewsRawDocument();
		newsdoc.setId(idBaomoi + "");
		newsdoc.setUrl(getRootURL(doc.getElementsByTag("fb:comments").attr("href")));
		
		String title = StringEscapeUtils.unescapeHtml4(doc.select("h1.title").text());
		if (title.equals(""))
			return null;
		newsdoc.setTitle(title);
		
		String date = JodaTimeParser.parseDate(StringEscapeUtils.unescapeHtml4(doc.select("span.time").text()));
		if (date.equals(""))
			return null;
		newsdoc.setDate(date);
		
		newsdoc.setSummary(StringEscapeUtils.unescapeHtml4(doc.select("div.story-body h2.summary").text()));
		newsdoc.setContent(StringEscapeUtils.unescapeHtml4(doc.select("div[itemprop=articleBody]").text()));
		
		return newsdoc;
	}	

	public static String getRootURL(String redirectURL) { 
		Document doc = URLFetcher.fetch(redirectURL);
		if (doc != null)
			return doc.baseUri();
		else
			return null;
	}
}
