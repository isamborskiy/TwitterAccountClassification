package com.samborskiy.feature.selection;

import com.samborskiy.feature.Feature;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.ConsistencySubsetEval;
import weka.attributeSelection.ScatterSearchV1;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class Cons_SS extends Feature {

    @Override
    protected ASSearch getSearcher() {
        return new ScatterSearchV1();
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new ConsistencySubsetEval();
    }
}
