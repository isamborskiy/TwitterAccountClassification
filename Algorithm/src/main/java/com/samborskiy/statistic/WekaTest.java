package com.samborskiy.statistic;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Type;
import com.samborskiy.entity.instances.functions.account.AccountFunction;
import com.samborskiy.entity.instances.functions.tweet.TweetFunction;
import com.samborskiy.feature.selection.FeatureSelection;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Whiplash on 01.05.2015.
 */
public class WekaTest extends Test {


    public WekaTest(Configuration configuration, String relationName, Map<Classifier, String> classifiers,
                    List<TweetFunction> tweetFunctions, List<AccountFunction> accountFunctions, List<FeatureSelection> featureSelections) {
        super(configuration, relationName, classifiers, tweetFunctions, accountFunctions, featureSelections);
    }

    @Override
    protected List<Statistic> test(Instances instances, int foldCount, FeatureSelection featureSelection) throws Exception {
        List<Statistic> statistics = new ArrayList<>();
        Instances newInstances = featureSelection.select(instances);
        for (Classifier classifier : classifiers.keySet()) {
            Evaluation evaluation = new Evaluation(newInstances);
            evaluation.crossValidateModel(classifier, newInstances, foldCount, new Random(1));
            statistics.add(new Statistic(getFMeasure(evaluation), evaluation.pctCorrect(), featureSelection.toString(), classifiers.get(classifier), newInstances.numAttributes()));
        }
        return statistics;
    }

    private double getFMeasure(Evaluation evaluation) {
        double fMeasure = 0.;
        for (Double val : getFMeasures(evaluation).values()) {
            fMeasure += val;
        }
        return fMeasure / configuration.getTypes().size();
    }

    private Map<Integer, Double> getFMeasures(Evaluation evaluation) {
        Map<Integer, Double> map = new HashMap<>();
        for (Type type : configuration.getTypes()) {
            map.put(type.getId(), evaluation.fMeasure(type.getId()));
        }
        return map;
    }
}
