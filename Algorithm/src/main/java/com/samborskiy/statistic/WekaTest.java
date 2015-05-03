package com.samborskiy.statistic;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Type;
import com.samborskiy.entity.instances.functions.AttributeFunction;
import com.samborskiy.feature.Feature;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Whiplash on 01.05.2015.
 */
public class WekaTest extends Test {

    public WekaTest(Configuration configuration, String relationName, Map<Classifier, String> classifiers,
                    List<AttributeFunction> attributeFunctions, List<Feature> features) {
        super(configuration, relationName, classifiers, attributeFunctions, features);
    }

    @Override
    protected Statistic test(Instances instances, int foldCount, Classifier classifier, String featureSelectionName) throws Exception {
        Evaluation evaluation = new Evaluation(instances);
        evaluation.crossValidateModel(classifier, instances, foldCount, new Random(1));
        return new Statistic(getFMeasure(evaluation), evaluation.pctCorrect(), featureSelectionName, classifiers.get(classifier), instances.numAttributes());
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
