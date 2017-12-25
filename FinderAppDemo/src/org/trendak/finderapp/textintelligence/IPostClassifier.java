package org.trendak.finderapp.textintelligence;

public interface IPostClassifier {
	public PostCategories classify(String rawText) throws Exception;

}
