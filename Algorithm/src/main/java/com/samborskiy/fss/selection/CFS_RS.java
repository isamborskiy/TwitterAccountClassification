package com.samborskiy.fss.selection;

import com.samborskiy.fss.FeatureSelection;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.RankSearch;

/**
 * Created by Whiplash on 30.04.2015.
 */
public class CFS_RS extends FeatureSelection {

    @Override
    protected ASSearch getSearcher() {
        return new RankSearch();
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new CfsSubsetEval();
    }
}
