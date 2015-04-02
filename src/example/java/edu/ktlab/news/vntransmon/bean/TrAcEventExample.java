package edu.ktlab.news.vntransmon.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.bean.TrAcEvent;

public class TrAcEventExample {

	public static void main(String[] args) {
		List<TrAcEvent> events = new ArrayList<TrAcEvent>();
		TrAcEvent event = new TrAcEvent();
		event.setTitle("tai nan xay ra tai Hanoi");
		event.setGeoLocEvent("20.868475,106.302234");
		for (int i = 0; i < 10; i++)
			events.add(event);
		Gson gson = new Gson();
		System.out.println(gson.toJson(events));
		
	}

}
