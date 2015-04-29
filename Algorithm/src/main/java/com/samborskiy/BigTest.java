package com.samborskiy;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by whiplash on 29.04.2015.
 */
public class BigTest {

    private static final String TRAIN_RELATION_NAME = "train";
    private static final String TEST_RELATION_NAME = "test";

    public static void main(String[] args) throws Exception {
        BufferedReader trainDataReader = new BufferedReader(new FileReader(TRAIN_RELATION_NAME + ".arff"));
        Instances trainData = new Instances(trainDataReader);
        trainData.setClassIndex(trainData.numAttributes() - 1);

        BufferedReader testDataReader = new BufferedReader(new FileReader(TEST_RELATION_NAME + ".arff"));
        Instances testData = new Instances(testDataReader);
        testData.setClassIndex(testData.numAttributes() - 1);

        Classifier classifier = new IBk();
        classifier.buildClassifier(trainData);

        double guess = 0.;
        for (Instance instance : testData) {
            if (classifier.classifyInstance(instance) == instance.classValue()) {
                guess++;
            }
        }
        System.out.println(guess / testData.size());
    }
}
