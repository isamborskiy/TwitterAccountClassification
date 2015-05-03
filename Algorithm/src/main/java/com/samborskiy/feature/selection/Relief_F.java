package com.samborskiy.feature.selection;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.Ranker;
import weka.attributeSelection.ReliefFAttributeEval;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class Relief_F extends FeatureSelection {

    @Override
    protected ASSearch getSearcher() {
        return new Ranker();
    }

    @Override
    protected String[] getSearcherOptions() {
        return new String[]{"-T", "0.01"};
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new ReliefFAttributeEval();
    }
}
