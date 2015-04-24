package com.samborskiy.test;

import com.samborskiy.entity.Configuration;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;

/**
 * Created by Whiplash on 24.04.2015.
 */
public class SVMTest extends Test {

    public SVMTest(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected Classifier getClassifier() {
        LibSVM svm = new LibSVM();
        return svm;
    }
}
