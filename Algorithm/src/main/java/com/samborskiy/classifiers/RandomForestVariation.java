package com.samborskiy.classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 04.05.2015.
 */
public class RandomForestVariation extends ClassifierVariation {

    {
        List<String> iValues = new ArrayList<>();
        for (int i = 90; i < 110; i++) {
            iValues.add(String.valueOf(i));
        }
        params.put("-I", iValues);
        paramsName.add("-I");

        List<String> kValues = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            kValues.add(String.valueOf(i));
        }
        params.put("-K", kValues);
        paramsName.add("-K");
    }

    @Override
    protected Classifier getClassifier() {
        return new RandomForest();
    }
}
