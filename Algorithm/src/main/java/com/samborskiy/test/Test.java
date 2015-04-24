package com.samborskiy.test;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Type;
import com.samborskiy.entity.instances.functions.AttributeFunction;
import com.samborskiy.weka.DatabaseToArff;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Whiplash on 24.04.2015.
 */
public abstract class Test {

    protected final Configuration configuration;
    protected Evaluation evaluation;

    public Test(Configuration configuration) {
        this.configuration = configuration;
    }

    public void test(String relationName, int foldCount, List<AttributeFunction> functions) throws Exception {
        DatabaseToArff.write(configuration, relationName, functions);

        BufferedReader datafile = new BufferedReader(new FileReader(relationName + ".arff"));
        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);
        Classifier classifier = getClassifier();

        evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, foldCount, new Random(1));
    }

    public double getAccuracy() {
        if (evaluation != null) {
            return evaluation.pctCorrect();
        }
        throw new IllegalStateException("Run test before getting result");
    }

    public double getFMeasure() {
        if (evaluation != null) {
            double fMeasure = 0.;
            for (Double val : getFMeasures().values()) {
                fMeasure += val;
            }
            return fMeasure / configuration.getTypes().size();
        }
        throw new IllegalStateException("Run test before getting result");
    }

    public Map<Integer, Double> getFMeasures() {
        if (evaluation != null) {
            Map<Integer, Double> map = new HashMap<>();
            for (Type type : configuration.getTypes()) {
                map.put(type.getId(), evaluation.fMeasure(type.getId()));
            }
            return map;
        }
        throw new IllegalStateException("Run test before getting result");
    }

    protected abstract Classifier getClassifier();
}
