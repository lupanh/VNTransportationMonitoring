package edu.ktlab.news.vntransmon.api;

public class GeocodingApiExample {

	public static void main(String[] args) throws Exception {
		String location = GetGeocoding
				.getGeoFirstResult("Km 50 + 200 thuộc xã Hoàng Diệu, huyện Gia Lộc, tỉnh Hải Dương");
		System.out.println(location);
	}

}
