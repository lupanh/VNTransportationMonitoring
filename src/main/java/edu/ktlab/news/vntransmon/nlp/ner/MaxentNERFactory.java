package edu.ktlab.news.vntransmon.nlp.ner;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.eval.FMeasure;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import edu.ktlab.news.vntransmon.nlp.tagger.TextSpan;

public class MaxentNERFactory {
	AdaptiveFeatureGenerator featureGenerator;

	public MaxentNERFactory(AdaptiveFeatureGenerator featureGenerator) {
		this.featureGenerator = featureGenerator;
	}

	public TokenNameFinderModel trainNER(InputStream trainStream, int iterator, int cutoff)
			throws Exception {
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(trainStream, charset);

		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);
		TokenNameFinderModel model;

		try {
			model = NameFinderME.train("en", "vnNer", sampleStream, featureGenerator,
					Collections.<String, Object> emptyMap(), iterator, cutoff);
		} finally {
			sampleStream.close();
		}

		return model;
	}

	public void trainNER(String trainingPath, String modelFilePath, int iterator, int cutoff)
			throws Exception {
		TokenNameFinderModel model = trainNER(new FileInputStream(trainingPath), iterator, cutoff);

		BufferedOutputStream modelOut = null;
		try {
			modelOut = new BufferedOutputStream(new FileOutputStream(modelFilePath));
			model.serialize(modelOut);
		} finally {
			if (modelOut != null) {
				modelOut.close();
			}
		}
	}

	public void evaluatebyExactMatching(String testPath, String modelPath) throws Exception {
		InputStream modelIn = new FileInputStream(modelPath);
		TokenNameFinderModel nerModel = new TokenNameFinderModel(modelIn);
		MaxentNEREvaluator evaluator = new MaxentNEREvaluator(new NameFinderME(nerModel,
				featureGenerator, 3));
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(testPath),
				charset);
		ObjectStream<NameSample> testStream = new NameSampleDataStream(lineStream);
		evaluator.evaluate(testStream);
		MicroFMeasure result = evaluator.getFMeasure();
		System.out.println(result.toString());
	}

	public void recognize(String testPath, String modelPath) throws Exception {
		InputStream modelIn = new FileInputStream(modelPath);
		TokenNameFinderModel nerModel = new TokenNameFinderModel(modelIn);
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(testPath),
				charset);
		ObjectStream<NameSample> testStream = new NameSampleDataStream(lineStream);
		NameSample sample;
		NameFinderME finder = new NameFinderME(nerModel, featureGenerator, 3);
		while ((sample = testStream.read()) != null) {
			Span[] spans = finder.find(sample.getSentence());
			System.out.println(TextSpan.getStringNameSample(spans, sample.getSentence()));
		}
	}

	public void nFoldEvaluate(String trainingPath, int numFolds, int iterator, int cutoff)
			throws Exception {
		FileInputStream sampleDataIn = new FileInputStream(trainingPath);
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(sampleDataIn.getChannel(),
				charset);
		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

		MaxentNERCrossValidator evaluator = new MaxentNERCrossValidator("en", cutoff, iterator,
				featureGenerator);

		evaluator.evaluate(sampleStream, numFolds);
		FMeasure result = evaluator.getFMeasure();
		System.out.println(result.toString());
		sampleDataIn.close();
	}
}
