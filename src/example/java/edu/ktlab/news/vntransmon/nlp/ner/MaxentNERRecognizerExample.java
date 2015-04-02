package edu.ktlab.news.vntransmon.nlp.ner;

import java.util.Scanner;

import edu.ktlab.news.vntransmon.nlp.tools.VNTWSSingleton;

public class MaxentNERRecognizerExample {
	public static void main(String[] args) throws Exception {
		MaxentNERRecognizer nerFinder = new MaxentNERRecognizer("models/ner/vntrans.model",
				MaxentNERFactoryExample1.createFeatureGenerator());
		System.out.print("Enter your sentence: ");
		Scanner scan = new Scanner(System.in);

		while (scan.hasNext()) {
			String text = scan.nextLine();
			if (text.equals("exit")) {
				break;
			}
			String[] tokens = VNTWSSingleton.getInstance().tokenize(text);
			String output = nerFinder.recognize(tokens);
			System.out.println(output);
			System.out.print("Enter your sentence: ");
		}
		scan.close();
	}

}
