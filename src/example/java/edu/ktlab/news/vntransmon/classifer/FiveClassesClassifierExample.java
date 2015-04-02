package edu.ktlab.news.vntransmon.classifer;

public class FiveClassesClassifierExample {
	String fileModel = "models/5-classes.model";
	String fileWordlist = "models/5-classes.wordlist";
	
	public static void main(String[] args) throws Exception {
		FiveClassesClassifier classifier = new FiveClassesClassifier();
		String sent = "TPO - Khoảng 21h ngày 24/3, trên đường Phạm Văn Đồng, Quốc lộ 14, đoạn qua thành phố Pleiku, tỉnh Gia Lai đã xảy ra vụ tai nạn giao thông khiến một thanh niên bị thương.";
		double score = classifier.classify(sent);
		System.out.println(classifier.getFeatureSet().getLabels().get((int) score));
	}

}
