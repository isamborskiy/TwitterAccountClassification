package com.samborskiy.fss.selection;

import com.samborskiy.fss.FeatureSelection;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class CFS_BiS extends FeatureSelection {

    @Override
    protected ASSearch getSearcher() {
        return new BestFirst();
    }

    @Override
    protected String[] getSearcherOptions() {
        return new String[]{"-D", "2"};
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new CfsSubsetEval();
    }
}
