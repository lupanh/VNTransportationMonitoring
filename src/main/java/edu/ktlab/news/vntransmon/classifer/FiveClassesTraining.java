package edu.ktlab.news.vntransmon.classifer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;

import de.bwaldvogel.liblinear.Train;
import edu.ktlab.news.vntransmon.bean.NewsRawDocument;
import edu.ktlab.news.vntransmon.classifer.feature.BOWFeatureGenerator;
import edu.ktlab.news.vntransmon.classifer.feature.ContextGenerator;
import edu.ktlab.news.vntransmon.classifer.feature.FeatureGenerator;
import edu.ktlab.news.vntransmon.classifer.feature.FeatureSet;
import edu.ktlab.news.vntransmon.classifer.feature.NGramFeatureGenerator;
import edu.ktlab.news.vntransmon.nlp.tools.SentenceSplitterSingleton;
import edu.ktlab.news.vntransmon.nlp.tools.VNTWSSingleton;
import edu.ktlab.news.vntransmon.util.FileHelper;

public class FiveClassesTraining {
	static String folderCorpus = "data/classifier/5-classes";
	static String fileTraining = "models/5-classes.training";
	static String fileModel = "models/5-classes.model";
	static String fileWordlist = "models/5-classes.wordlist";

	@SuppressWarnings("unchecked")
	public static FeatureGenerator<String[], String>[] mFeatureGenerators = new FeatureGenerator[] {
			new BOWFeatureGenerator(), new NGramFeatureGenerator(2), new NGramFeatureGenerator(3) };

	static ContextGenerator contextGenerator;
	static FeatureSet featureSet;

	public static void init() throws Exception {
		contextGenerator = new ContextGenerator(mFeatureGenerators);
		featureSet = new FeatureSet();
	}

	private static void createVectorTrainingFile() throws Exception {
		Gson gson = new Gson();

		File folder = new File(folderCorpus);
		if (!folder.isDirectory())
			System.exit(0);
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileTraining)));
		for (File subFolder : folder.listFiles()) {
			String label = subFolder.getName();
			for (File file : subFolder.listFiles()) {
				NewsRawDocument document = new NewsRawDocument();
				document = gson.fromJson(FileHelper.readFileAsString(file), NewsRawDocument.class);
				String content = document.getTitle() + "\n" + document.getSummary();
				String[] sents = SentenceSplitterSingleton.getInstance().split(content);
				ArrayList<String> features = new ArrayList<String>();
				for (String sent : sents) {
					String[] tokens = VNTWSSingleton.getInstance().segment(sent).toLowerCase()
							.split(" ");
					ArrayList<String> fts = contextGenerator.getContext(tokens, sent);
					features.addAll(fts);
				}

				String vector = featureSet.addprintVector(features, label, false);
				if (vector.equals("")) {
					continue;
				}

				writer.append(vector).append("\n");
			}
		}

		writer.close();

		FileHelper.writeObjectToFile(featureSet, new File(fileWordlist));
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		long current = System.currentTimeMillis();
		init();
		createVectorTrainingFile();
		// Training
		new Train().main(new String[] { "-s", "2", fileTraining, fileModel });

		// Cross-validation
		// new Train().main(new String[] { "-v", "10", "-s", "1", fileTraining, fileModel });

		System.out.println("Processing time: " + (System.currentTimeMillis() - current) + " ms");
	}
}
