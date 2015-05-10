package com.samborskiy.feature;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class NoFeatureSelection extends Feature {

    @Override
    protected ASSearch getSearcher() {
        return null;
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return null;
    }
}
