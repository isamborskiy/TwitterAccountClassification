package com.samborskiy.classifier.fss.extraction;

import com.samborskiy.classifier.fss.FeatureSelection;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.PrincipalComponents;
import weka.attributeSelection.Ranker;

/**
 * Created by Whiplash on 03.05.2015.
 */
public class PCA extends FeatureSelection {

    @Override
    protected ASSearch getSearcher() {
        return new Ranker();
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return new PrincipalComponents();
    }
}
