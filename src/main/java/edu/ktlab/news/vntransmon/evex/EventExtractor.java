package edu.ktlab.news.vntransmon.evex;

import opennlp.tools.util.Span;

import com.google.gson.Gson;

import edu.ktlab.news.vntransmon.api.GetGeocoding;
import edu.ktlab.news.vntransmon.bean.TrAcEvent;
import edu.ktlab.news.vntransmon.nlp.ner.MaxentNERFactoryExample1;
import edu.ktlab.news.vntransmon.nlp.ner.MaxentNERRecognizer;
import edu.ktlab.news.vntransmon.nlp.tagger.TextSpan;
import edu.ktlab.news.vntransmon.nlp.tools.SentenceSplitterSingleton;
import edu.ktlab.news.vntransmon.nlp.tools.VNTWSSingleton;

public class EventExtractor {
	Gson gson = new Gson();
	MaxentNERRecognizer nerFinder;
	String modelNer = "models/ner/vntrans.2.0.model";

	public EventExtractor() throws Exception {
		nerFinder = new MaxentNERRecognizer(modelNer,
				MaxentNERFactoryExample1.createFeatureGenerator());
	}

	public EventExtractor(String model) throws Exception {
		nerFinder = new MaxentNERRecognizer(model,
				MaxentNERFactoryExample1.createFeatureGenerator());
	}

	public TrAcEvent extractSummary(String json, boolean googleGeo) throws Exception {
		TrAcEvent doc = new TrAcEvent();
		doc = gson.fromJson(json, TrAcEvent.class);
		
		return extractSummary(doc, googleGeo);
	}
	
	public TrAcEvent extractSummary(TrAcEvent doc, boolean googleGeo) throws Exception {
		String[] sents = SentenceSplitterSingleton.getInstance().split(doc.getSummary());

		String locs = "";
		String times = "";
		String reasons = "";
		String dames = "";
		String vehicles = "";

		for (String sent : sents) {
			String[] tokens = VNTWSSingleton.getInstance().tokenize(sent);

			Span[] spans = nerFinder.find(tokens);
			for (Span span : spans) {
				String entity = TextSpan.spanToString(span, tokens).replaceAll("_", " ");
				switch (span.getType()) {
				case "loc":
					locs += entity + ";";
					break;
				case "time":
					times += entity + ";";
					break;
				case "reason":
					reasons += entity + ";";
					break;
				case "dame":
					dames += entity + ";";
					break;
				case "vehicle":
					vehicles += entity + ";";
					break;
				}
			}
		}
		doc.setWhereEvent(locs);
		if (googleGeo)
			if (!locs.equals(""))
				doc.setGeoLocEvent(GetGeocoding.getGeoFirstResult(locs));
		doc.setWhenEvent(times);
		doc.setVehicleEvent(vehicles);
		doc.setDameEvent(dames);
		doc.setReasonEvent(reasons);
		if (doc.getUrl() == null)
			doc.setUrl("http://www.baomoi.com/a-b-c/141/" + doc.getId() + ".epi");

		return doc;
	}

}
