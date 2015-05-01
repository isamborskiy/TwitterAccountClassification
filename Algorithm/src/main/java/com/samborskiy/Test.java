package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.functions.account.AccountFunction;
import com.samborskiy.entity.instances.functions.tweet.TweetFunction;
import com.samborskiy.feature.selection.FeatureSelection;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
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
    protected final List<Statistic> result;

    public Test(Configuration configuration, String relationName, Map<Classifier, String> classifiers,
                List<TweetFunction> tweetFunctions, List<AccountFunction> accountFunctions, List<FeatureSelection> featureSelections) {
        this.configuration = configuration;
        this.relationName = relationName;
        this.classifiers = classifiers;
        this.tweetFunctions = tweetFunctions;
        this.accountFunctions = accountFunctions;
        this.featureSelections = featureSelections;
        this.result = new ArrayList<>();
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
            result.add(new Statistic(configuration, evaluation));
        }
    }
}
