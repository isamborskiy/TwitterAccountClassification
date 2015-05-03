//package com.samborskiy.feature.selection;
//
//import weka.attributeSelection.ASEvaluation;
//import weka.attributeSelection.ASSearch;
//import weka.attributeSelection.ConsistencySubsetEval;
//import weka.attributeSelection.INTERACT;
//
///**
// * Created by Whiplash on 30.04.2015.
// */
//public class INTERACT_C extends FeatureSelection {
//
//    @Override
//    protected ASSearch getSearcher() {
//        return new INTERACT();
//    }
//
//    @Override
//    protected String[] getSearcherOptions() {
//        return new String[]{"-T", "0.0001"};
//    }
//
//    @Override
//    protected ASEvaluation getEvaluator() {
//        return new ConsistencySubsetEval();
//    }
//}
