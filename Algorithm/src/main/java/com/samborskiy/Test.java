package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Type;
import com.samborskiy.entity.instances.functions.account.AccountFunction;
import com.samborskiy.entity.instances.functions.tweet.TweetFunction;
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
public class Test {

    protected final Configuration configuration;
    protected Evaluation evaluation;

    public Test(Configuration configuration) {
        this.configuration = configuration;
    }

    public void test(String relationName, int foldCount, Map<Classifier, String> classifiers, List<TweetFunction> tweetFunctions, List<AccountFunction> accountFunctions) throws Exception {
//        DatabaseToArff.write(configuration, relationName, tweetFunctions, accountFunctions);

        BufferedReader datafile = new BufferedReader(new FileReader(relationName + ".arff"));
        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);

        for (Classifier classifier : classifiers.keySet()) {
            evaluation = new Evaluation(data);
            evaluation.crossValidateModel(classifier, data, foldCount, new Random(1));
            System.out.format("Classifier: %s\nStatistics:\n%s\n", classifiers.get(classifier), getStatistics());
        }
    }

    private String getStatistics() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Accuracy: %f\n", getAccuracy()));
        builder.append(String.format("F-measure: %f\n", getFMeasure()));
        Map<Integer, Double> map = getFMeasures();
        for (Integer classId : map.keySet()) {
            builder.append(String.format("F-measure of %d class is %f\n", classId, map.get(classId)));
        }
        return builder.toString();
    }

    private double getAccuracy() {
        if (evaluation != null) {
            return evaluation.pctCorrect();
        }
        throw new IllegalStateException("Run test before getting result");
    }

    private double getFMeasure() {
        if (evaluation != null) {
            double fMeasure = 0.;
            for (Double val : getFMeasures().values()) {
                fMeasure += val;
            }
            return fMeasure / configuration.getTypes().size();
        }
        throw new IllegalStateException("Run test before getting result");
    }

    private Map<Integer, Double> getFMeasures() {
        if (evaluation != null) {
            Map<Integer, Double> map = new HashMap<>();
            for (Type type : configuration.getTypes()) {
                map.put(type.getId(), evaluation.fMeasure(type.getId()));
            }
            return map;
        }
        throw new IllegalStateException("Run test before getting result");
    }
}
