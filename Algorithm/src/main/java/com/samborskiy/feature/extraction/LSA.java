package com.samborskiy.feature.extraction;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.LatentSemanticAnalysis;
import weka.attributeSelection.Ranker;

/**
 * Created by Whiplash on 04.05.2015.
 */
public class LSA extends FeatureExtraction {

    @Override
    protected ASSearch getSearcher() {
        return new Ranker();
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new LatentSemanticAnalysis();
    }
}
