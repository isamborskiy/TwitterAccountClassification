package com.samborskiy.test;

import com.samborskiy.entity.Configuration;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

/**
 * Created by Whiplash on 24.04.2015.
 */
public class DecisionTreeTest extends Test {

    public DecisionTreeTest(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected Classifier getClassifier() {
        return new J48();
    }
}
