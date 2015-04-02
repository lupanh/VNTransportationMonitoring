package edu.ktlab.news.vntransmon.api;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class GetGeocoding {
	static GeoApiContext context = new GeoApiContext()
			.setApiKey("AIzaSyBjNXvoJHjG3G2UJKIw2vnCfKQD46l2NBI");

	public static String getGeoFirstResult(String location) {
		GeocodingResult[] results = getGeoResult(location);
		if (results == null)
			return "";
		if (results.length == 0)
			return "";
		return results[0].geometry.location.toString();
	}

	public static GeocodingResult[] getGeoResult(String location) {
		try {
			return GeocodingApi.geocode(context, location).await();
		} catch (Exception e) {
			return null;
		}
	}
}
