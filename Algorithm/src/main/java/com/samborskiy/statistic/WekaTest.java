package com.samborskiy.statistic;

import com.samborskiy.classifiers.ClassifierWrapper;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Type;
import com.samborskiy.entity.functions.AccountFunction;
import com.samborskiy.classifier.fss.FeatureSelection;
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

    public WekaTest(Configuration configuration, String relationName, List<ClassifierWrapper> classifiers,
                    List<AccountFunction> accountFunctions, List<FeatureSelection> features) {
        super(configuration, relationName, classifiers, accountFunctions, features);
    }

    @Override
    protected Statistic test(Instances instances, int foldCount, ClassifierWrapper classifierWrapper, String featureSelectionName) throws Exception {
        Evaluation evaluation = new Evaluation(instances);
        evaluation.crossValidateModel(classifierWrapper.getClassifier(), instances, foldCount, new Random(1));
        return new Statistic(getFMeasure(evaluation), evaluation.pctCorrect() / 100, featureSelectionName, classifierWrapper.toString(), instances.numAttributes());
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
