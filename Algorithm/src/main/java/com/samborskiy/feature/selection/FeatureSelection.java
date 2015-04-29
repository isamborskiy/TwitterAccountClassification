package com.samborskiy.feature.selection;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

/**
 * Created by Whiplash on 30.04.2015.
 */
public abstract class FeatureSelection {

    public Instances select(Instances instances) {
        AttributeSelection filter = new AttributeSelection();
        filter.setSearch(getSearcher());
        filter.setEvaluator(getEvaluator());
        try {
            filter.setInputFormat(instances);
            return Filter.useFilter(instances, filter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected abstract ASSearch getSearcher();

    protected abstract ASEvaluation getEvaluator();
}
