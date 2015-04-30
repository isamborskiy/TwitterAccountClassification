package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Type;
import com.samborskiy.entity.instances.functions.account.AccountFunction;
import com.samborskiy.entity.instances.functions.tweet.TweetFunction;
import com.samborskiy.feature.selection.FeatureSelection;
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
    protected final String relationName;
    protected final Map<Classifier, String> classifiers;
    protected final List<TweetFunction> tweetFunctions;
    protected final List<AccountFunction> accountFunctions;
    protected final List<FeatureSelection> featureSelections;

    public Test(Configuration configuration, String relationName, Map<Classifier, String> classifiers,
                List<TweetFunction> tweetFunctions, List<AccountFunction> accountFunctions) {
        this(configuration, relationName, classifiers, tweetFunctions, accountFunctions, null);
    }

    public Test(Configuration configuration, String relationName, Map<Classifier, String> classifiers,
                List<TweetFunction> tweetFunctions, List<AccountFunction> accountFunctions, List<FeatureSelection> featureSelections) {
        this.configuration = configuration;
        this.relationName = relationName;
        this.classifiers = classifiers;
        this.tweetFunctions = tweetFunctions;
        this.accountFunctions = accountFunctions;
        this.featureSelections = featureSelections;
    }

    public void test(int foldCount) throws Exception {
        //        DatabaseToArff.write(configuration, relationName, tweetFunctions, accountFunctions);

        BufferedReader datafile = new BufferedReader(new FileReader(relationName + ".arff"));
        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);

        if (featureSelections == null) {
            test(data, foldCount);
        } else {
            for (FeatureSelection featureSelection : featureSelections) {
                Instances newData = featureSelection.select(data);
                System.out.format("-------------------------------\n%s\nRemain attributes: %d\n\n", featureSelection, newData.numAttributes());
                test(newData, foldCount);
                System.out.format("-------------------------------\n");
            }
        }
    }

    private void test(Instances instances, int foldCount) throws Exception {
        for (Classifier classifier : classifiers.keySet()) {
            Evaluation evaluation = new Evaluation(instances);
            evaluation.crossValidateModel(classifier, instances, foldCount, new Random(1));
            System.out.format("Classifier: %s\nStatistics:\n%s\n", classifiers.get(classifier), getStatistics(evaluation));
        }
    }

    private String getStatistics(Evaluation evaluation) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Accuracy: %f\n", getAccuracy(evaluation)));
        builder.append(String.format("F-measure: %f\n", getFMeasure(evaluation)));
        Map<Integer, Double> map = getFMeasures(evaluation);
        for (Integer classId : map.keySet()) {
            builder.append(String.format("F-measure of %d class is %f\n", classId, map.get(classId)));
        }
        return builder.toString();
    }

    private double getAccuracy(Evaluation evaluation) {
        if (evaluation != null) {
            return evaluation.pctCorrect();
        }
        throw new IllegalStateException("Run test before getting result");
    }

    private double getFMeasure(Evaluation evaluation) {
        if (evaluation != null) {
            double fMeasure = 0.;
            for (Double val : getFMeasures(evaluation).values()) {
                fMeasure += val;
            }
            return fMeasure / configuration.getTypes().size();
        }
        throw new IllegalStateException("Run test before getting result");
    }

    private Map<Integer, Double> getFMeasures(Evaluation evaluation) {
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
