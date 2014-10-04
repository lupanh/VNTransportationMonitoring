package edu.ktlab.news.vntransmon.fetcher;

public class BaomoiRootURLExample {

	public static void main(String[] args) {
		String url = "http://www.baomoi.com/Home/CNTT/gamek.vn/Clip-Nen-do-hoa-long-lay-trong-Xa-Dieu-ZERO/14968256.epi";
		System.out.println(BaomoiFetcher.getRootURL(url));
	}

}
