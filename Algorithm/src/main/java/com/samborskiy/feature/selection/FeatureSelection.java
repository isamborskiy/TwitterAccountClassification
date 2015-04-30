package com.samborskiy.feature.selection;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

/**
 * Created by Whiplash on 30.04.2015.
 */
public abstract class FeatureSelection {

    public Instances select(Instances instances) {
        try {
            AttributeSelection filter = new AttributeSelection();
            ASSearch searcher = getSearcher();
            if (searcher instanceof OptionHandler) {
                ((OptionHandler) searcher).setOptions(getOptions());
            }
            filter.setSearch(searcher);
            filter.setEvaluator(getEvaluator());
            filter.setInputFormat(instances);
            return Filter.useFilter(instances, filter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected abstract ASSearch getSearcher();

    protected abstract String[] getOptions();

    protected abstract ASEvaluation getEvaluator();

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
