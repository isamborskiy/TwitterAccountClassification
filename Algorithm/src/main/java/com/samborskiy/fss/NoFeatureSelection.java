package com.samborskiy.fss;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class NoFeatureSelection extends FeatureSelection {

    @Override
    protected ASSearch getSearcher() {
        return null;
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return null;
    }
}
