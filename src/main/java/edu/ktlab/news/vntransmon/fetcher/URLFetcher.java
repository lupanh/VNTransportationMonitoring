package edu.ktlab.news.vntransmon.fetcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.ktlab.news.vntransmon.util.PropertyLoader;

public class URLFetcher {
	String url;
	Document doc;
	
	public URLFetcher(String url) {
		this.url = url;
		doc = fetch(url);
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getSource() {
		return doc.html();
	}
	
	public Document getDocument() {
		return doc;
	}
	
	public List<String> getLinks() {
		if (doc == null)
			return null;
		List<String> links = new ArrayList<String>();
		Elements elements = doc.select("a[href]");
		for (Element element : elements) {
			String link = element.attr("href");
			if (!link.contains("javascript:")) 
				links.add(link);
		}
		return links;
	}
	
	public List<URL> getFullLinks() {
		List<URL> fullLinks = new ArrayList<URL>();
		List<String> links = getLinks();
		
		if (links == null)
			return null;
		
		for (String link : links) {
			try {
				URL fullUrl = new URL(new URL(url), link);
				fullLinks.add(fullUrl);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}				
		return fullLinks;
	}
	
	public static Document fetch(String url) {
		Document doc;
		try {
			doc = Jsoup
					.connect(url)
					.userAgent("Mozilla")
					.cookie("auth", "token")
					.timeout(Integer.parseInt(PropertyLoader.getInstance().getProperties("FETCHER_TIMEOUT")))
					.get();
		} catch (Exception e) {
			return null;
		}
		return doc;
	}
}
