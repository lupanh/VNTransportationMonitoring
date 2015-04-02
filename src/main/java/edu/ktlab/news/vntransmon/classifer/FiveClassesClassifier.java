package edu.ktlab.news.vntransmon.classifer;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import de.bwaldvogel.liblinear.FeatureNode;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import edu.ktlab.news.vntransmon.classifer.feature.ContextGenerator;
import edu.ktlab.news.vntransmon.classifer.feature.FeatureSet;
import edu.ktlab.news.vntransmon.data.NewsRawDocument;
import edu.ktlab.news.vntransmon.nlp.tools.SentenceSplitterSingleton;
import edu.ktlab.news.vntransmon.nlp.tools.VNTWSSingleton;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class FiveClassesClassifier {
	String fileModel = "models/5-classes.model";
	String fileWordlist = "models/5-classes.wordlist";

	ContextGenerator contextGenerator;
	Model model;
	FeatureSet featureSet;

	public void init() throws Exception {
		contextGenerator = new ContextGenerator(TwoClassesTraining.mFeatureGenerators);
	}

	public FiveClassesClassifier() throws Exception {
		init();
		loadModel();
		loadWordlist();
	}

	public FiveClassesClassifier(String model, String wordlist) throws Exception {
		this.fileModel = model;
		this.fileWordlist = wordlist;
		init();
		loadModel();
		loadWordlist();
	}

	void loadWordlist() throws Exception {
		featureSet = (FeatureSet) FileHelper.readObjectFromFile(new File(fileWordlist));
	}

	void loadModel() throws Exception {
		model = Linear.loadModel(new File(fileModel));
	}

	public double classify(String text) throws Exception {
		String[] sents = SentenceSplitterSingleton.getInstance().split(text);

		ArrayList<String> features = new ArrayList<String>();
		for (String sent : sents) {
			String[] tokens = VNTWSSingleton.getInstance().segment(sent).toLowerCase().split(" ");
			ArrayList<String> fts = contextGenerator.getContext(tokens, sent);
			features.addAll(fts);
		}

		TreeMap<Integer, Integer> vector = featureSet.addStringFeatureVector(features, "", true);
		ArrayList<FeatureNode> vfeatures = new ArrayList<FeatureNode>();

		if (vector == null)
			return -1;
		for (int key : vector.keySet()) {
			if (key == featureSet.getLabelKey())
				continue;
			FeatureNode featurenode = new FeatureNode(key, vector.get(key));
			vfeatures.add(featurenode);
		}

		double output = Linear.predict(model, vfeatures.toArray(new FeatureNode[vfeatures.size()]));
		return output;
	}

	public double classify(NewsRawDocument document) throws Exception {
		return classify(document.getTitle() + "\n" + document.getSummary());
	}

	public FeatureSet getFeatureSet() {
		return featureSet;
	}

	public void setFeatureSet(FeatureSet featureSet) {
		this.featureSet = featureSet;
	}

	public String getLabel(double score) {
		return featureSet.getLabels().get((int) score);
	}
}
