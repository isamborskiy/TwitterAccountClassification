package com.samborskiy.feature.selection;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GeneticSearch;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class CFS_GS extends FeatureSelection {

    @Override
    protected ASSearch getSearcher() {
        return new GeneticSearch();
    }

    @Override
    protected String[] getOptions() {
        return new String[]{};
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new CfsSubsetEval();
    }
}