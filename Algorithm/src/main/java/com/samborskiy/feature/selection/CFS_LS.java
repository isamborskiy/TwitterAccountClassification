package com.samborskiy.feature.selection;

import com.samborskiy.feature.Feature;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.LinearForwardSelection;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class CFS_LS extends Feature {

    @Override
    protected ASSearch getSearcher() {
        return new LinearForwardSelection();
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new CfsSubsetEval();
    }
}
