package com.samborskiy.statistic;

import com.samborskiy.classifiers.ClassifierWrapper;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.functions.AttributeFunction;
import com.samborskiy.feature.Feature;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Whiplash on 02.05.2015.
 */
public class ConfusionMatrixTest extends Test {

    private static final int ROUNDS = 50;

    public ConfusionMatrixTest(Configuration configuration, String relationName, List<ClassifierWrapper> classifiers,
                               List<AttributeFunction> attributeFunctions, List<Feature> features) {
        super(configuration, relationName, classifiers, attributeFunctions, features);
    }

    @Override
    protected Statistic test(Instances instances, int foldCount, ClassifierWrapper classifierWrapper, String featureSelectionName) throws Exception {
        double fMeasure = 0.;
        double accuracy = 0.;
        Classifier classifier = classifierWrapper.getClassifier();
        for (int t = 0; t < ROUNDS; t++) {
            Collections.shuffle(instances);
            for (int i = 0; i < foldCount; i++) {
                int[][] confusionMatrix = new int[instances.numClasses()][instances.numClasses()];
                Instances test = instances.testCV(foldCount, i);
                Instances train = instances.trainCV(foldCount, i);
                classifier.buildClassifier(train);
                double acc = 0.;
                for (Instance instance : test) {
                    int classId = (int) classifier.classifyInstance(instance);
                    if (classId == instance.classValue()) {
                        acc++;
                    }
                    confusionMatrix[classId][((int) instance.classValue())]++;
                }
                accuracy += acc / test.size();
                fMeasure += getFMeasure(confusionMatrix);
            }
        }
        accuracy /= (ROUNDS * foldCount);
        fMeasure /= (ROUNDS * foldCount);
        return new Statistic(fMeasure, accuracy, featureSelectionName, classifierWrappers.toString(), instances.numAttributes());
    }

    private double getFMeasure(int[][] confusionMatrix) {
        double precision = getPrecision(confusionMatrix);
        double recall = getRecall(confusionMatrix);
        return 2 * precision * recall / (precision + recall);
    }

    private double getPrecision(int[][] confusionMatrix) {
        double[] precisions = new double[confusionMatrix.length];
        for (int i = 0; i < precisions.length; i++) {
            for (int j = 0; j < precisions.length; j++) {
                precisions[i] += confusionMatrix[i][j];
            }
            precisions[i] = confusionMatrix[i][i] / precisions[i];
        }
        return Arrays.stream(precisions).average().getAsDouble();
    }

    private double getRecall(int[][] confusionMatrix) {
        double[] recalls = new double[confusionMatrix.length];
        for (int i = 0; i < recalls.length; i++) {
            for (int j = 0; j < recalls.length; j++) {
                recalls[i] += confusionMatrix[j][i];
            }
            recalls[i] = confusionMatrix[i][i] / recalls[i];
        }
        return Arrays.stream(recalls).average().getAsDouble();
    }
}
