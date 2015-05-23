package com.samborskiy.statistic;

import com.samborskiy.InformationGain;
import com.samborskiy.classifiers.ClassifierWrapper;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Type;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Whiplash on 20.05.2015.
 */
public class ConfusionMatrixTestAttributes {

    protected final Configuration configuration;
    protected final String relationName;

    private static final int ROUNDS = 50;

    public ConfusionMatrixTestAttributes(Configuration configuration, String relationName) {
        this.configuration = configuration;
        this.relationName = relationName;
    }

    public List<Statistic> test() throws Exception {
        BufferedReader datafile = new BufferedReader(new FileReader(relationName + ".arff"));
        Instances instances = new Instances(datafile);
        instances.setClassIndex(instances.numAttributes() - 1);
        instances.deleteAttributeAt(instances.attribute("retweet_number").index());

        Set<Map.Entry<String, Double>> sortedStatistics = InformationGain.getStatistics();
        List<Statistic> statistics = new ArrayList<>();
        statistics.add(getStatistic(instances, 5, new J48()));
        for (Map.Entry<String, Double> entry : sortedStatistics) {
            System.out.println(instances.numAttributes());
            if (instances.numAttributes() != 1) {
                Attribute attribute = instances.attribute(entry.getKey());
                instances.deleteAttributeAt(attribute.index());
                statistics.add(getStatistic(instances, 5, new J48()));
            }
        }
        return statistics;
    }

    protected Statistic getStatistic(Instances instances, int foldCount, Classifier classifier) throws Exception {
        Evaluation evaluation = new Evaluation(instances);
        evaluation.crossValidateModel(classifier, instances, foldCount, new Random(1));
        return new Statistic(getFMeasure(evaluation), evaluation.pctCorrect() / 100, "", "", instances.numAttributes());
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

//    public Statistic getStatistic(Instances instances, int foldCount, Classifier classifier) throws Exception {
//        double fMeasure = 0.;
//        double accuracy = 0.;
//        for (int t = 0; t < ROUNDS; t++) {
//            Collections.shuffle(instances);
//            for (int i = 0; i < foldCount; i++) {
//                int[][] confusionMatrix = new int[instances.numClasses()][instances.numClasses()];
//                Instances test = instances.testCV(foldCount, i);
//                Instances train = instances.trainCV(foldCount, i);
//                classifier.buildClassifier(train);
//                double acc = 0.;
//                for (Instance instance : test) {
//                    int classId = (int) classifier.classifyInstance(instance);
//                    if (classId == instance.classValue()) {
//                        acc++;
//                    }
//                    confusionMatrix[classId][((int) instance.classValue())]++;
//                }
//                accuracy += acc / test.size();
//                fMeasure += getFMeasure(confusionMatrix);
//            }
//        }
//        accuracy /= (ROUNDS * foldCount);
//        fMeasure /= (ROUNDS * foldCount);
//        return new Statistic(fMeasure, accuracy, "", "", instances.numAttributes());
//    }

    private double getFMeasure(int[][] confusionMatrix) {
        double precision = getPrecision(confusionMatrix);
        double recall = getRecall(confusionMatrix);
        return 2 * precision * recall / (precision + recall);
    }

    private double getPrecision(int[][] confusionMatrix) {
        double[] precisions = new double[confusionMatrix.length];
        for (int i = 0; i < precisions.length; i++) {
            if (confusionMatrix[i][i] != 0) {
                for (int j = 0; j < precisions.length; j++) {
                    precisions[i] += confusionMatrix[i][j];
                }
                precisions[i] = confusionMatrix[i][i] / precisions[i];
            }
        }
        return Arrays.stream(precisions).average().getAsDouble();
    }

    private double getRecall(int[][] confusionMatrix) {
        double[] recalls = new double[confusionMatrix.length];
        for (int i = 0; i < recalls.length; i++) {
            if (confusionMatrix[i][i] != 0) {
                for (int j = 0; j < recalls.length; j++) {
                    recalls[i] += confusionMatrix[j][i];
                }
                recalls[i] = confusionMatrix[i][i] / recalls[i];
            }
        }
        return Arrays.stream(recalls).average().getAsDouble();
    }
}
