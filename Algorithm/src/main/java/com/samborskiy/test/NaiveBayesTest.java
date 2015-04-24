package com.samborskiy.test;

import com.samborskiy.entity.Configuration;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;

/**
 * Created by Whiplash on 24.04.2015.
 */
public class NaiveBayesTest extends Test {

    public NaiveBayesTest(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected Classifier getClassifier() {
        return new NaiveBayes();
    }
}
