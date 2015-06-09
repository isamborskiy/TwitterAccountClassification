package com.samborskiy.fss.selection;

import com.samborskiy.fss.FeatureSelection;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.ConsistencySubsetEval;
import weka.attributeSelection.ScatterSearchV1;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class Cons_SS extends FeatureSelection {

    @Override
    protected ASSearch getSearcher() {
        return new ScatterSearchV1();
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new ConsistencySubsetEval();
    }
}
