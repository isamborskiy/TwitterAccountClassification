package com.samborskiy.feature;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

/**
 * Created by Whiplash on 03.05.2015.
 */
public abstract class Feature {

    public Instances select(Instances instances) {
        try {
            AttributeSelection filter = new AttributeSelection();
            ASSearch searcher = getSearcher();
            ASEvaluation evaluation = getEvaluator();
            if (searcher != null && evaluation != null) {
                if (searcher instanceof OptionHandler) {
                    ((OptionHandler) searcher).setOptions(getSearcherOptions());
                }
                if (evaluation instanceof OptionHandler) {
                    ((OptionHandler) evaluation).setOptions(getEvaluatorOptions());
                }
                filter.setSearch(searcher);
                filter.setEvaluator(evaluation);
                filter.setInputFormat(instances);
                return Filter.useFilter(instances, filter);
            } else {
                return instances;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected abstract ASSearch getSearcher();

    protected String[] getSearcherOptions() {
        return new String[]{};
    }

    protected abstract ASEvaluation getEvaluator();

    protected String[] getEvaluatorOptions() {
        return new String[]{};
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
