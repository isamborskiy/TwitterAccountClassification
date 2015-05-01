package com.samborskiy.feature.selection;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.ConsistencySubsetEval;
import weka.attributeSelection.LinearForwardSelection;
import weka.attributeSelection.RankSearch;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class Cons_RS extends FeatureSelection {

    @Override
    protected ASSearch getSearcher() {
        return new RankSearch();
    }

    @Override
    protected String[] getOptions() {
        return new String[]{};
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new ConsistencySubsetEval();
    }
}
