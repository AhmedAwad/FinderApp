package org.trendak.finderapp.textintelligence.machinelearning.postclassification;

import java.util.ArrayList;

import org.trendak.finderapp.textintelligence.IPostClassifier;
import org.trendak.finderapp.textintelligence.PostCategories;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class BayesianClassifier implements IPostClassifier{

	public static DataSource source;
	public static Instances trainDataset;

	public static FilteredClassifier cls_co;
	public static Instances data;
	public static double same = 0;
	public static Instance inst_co;
	public static Attribute Text;
	@Override
	public  PostCategories classify(String text) throws Exception {

		cls_co = (FilteredClassifier) weka.core.SerializationHelper.read("nBayes.model");

		ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2);

		Text = new Attribute("Text", true);
		ArrayList<String> classVal = new ArrayList<String>();
		classVal.add("Irrelevant");
		classVal.add("Found");
		classVal.add("Found_Suspected");
		classVal.add("Lost"); 
		classVal.add("Found_Dead");

		attributeList.add(Text);
		attributeList.add(new Attribute("class", classVal));
 
		data = new Instances("TestInstances", attributeList, 0);
		data.setClassIndex(data.numAttributes() - 1);
		inst_co = new DenseInstance(data.numAttributes());
		inst_co.setDataset(data);
		buildData(text); 
		
		DenseInstance inst = (DenseInstance) data.instance(0); 
		int result = (int) cls_co.classifyInstance(inst); 
		//TODO: [Ahmed Awad] Changed to match the interface
		return PostCategories.values()[result];


	}

	public static void buildData(String txt) {

		try {

			inst_co.setValue(Text, Text.addStringValue(txt));
			//inst_co.setClassValue(index);
			data.add(inst_co);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}