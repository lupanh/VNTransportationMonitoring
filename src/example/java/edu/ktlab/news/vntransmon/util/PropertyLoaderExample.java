package edu.ktlab.news.vntransmon.util;

public class PropertyLoaderExample {
	public static void main(String[] args) {
		System.out.println(PropertyLoader.getInstance().getProperties("ELASTIC_SERVER"));
		System.out.println(PropertyLoader.getInstance().getProperties("NUM_THREAD"));
		System.out.println(PropertyLoader.getInstance().getProperties("INDEX_NAME"));
	}
}
