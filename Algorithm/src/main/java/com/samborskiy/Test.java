package com.samborskiy;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.Random;

/**
 * Created by Whiplash on 10.06.2015.
 */
class Test {

    private final Classifier classifier;
    private final Instances instances;

    public Test(Classifier classifier, Instances instances) {
        this.instances = instances;
        this.classifier = classifier;
    }

    public double crossValidation(int folds) throws Exception {
        Evaluation evaluation = new Evaluation(instances);
        evaluation.crossValidateModel(classifier, instances, folds, new Random(1));
        return getFMeasure(evaluation, instances.numClasses());
    }

    private double getFMeasure(Evaluation evaluation, int classNumber) {
        double precision = 0.;
        double recall = 0.;
        for (int i = 0; i < classNumber; i++) {
            precision += evaluation.precision(i);
            recall += evaluation.recall(i);
        }
        precision /= 3;
        recall /= 3;
        return 2 * precision * recall / (precision + recall);
    }
}
