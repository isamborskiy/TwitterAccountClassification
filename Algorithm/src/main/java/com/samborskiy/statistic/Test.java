package com.samborskiy.statistic;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.functions.account.AccountFunction;
import com.samborskiy.entity.instances.functions.tweet.TweetFunction;
import com.samborskiy.feature.selection.FeatureSelection;
import com.samborskiy.weka.DatabaseToArff;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Whiplash on 01.05.2015.
 */
public abstract class Test {

    protected final Configuration configuration;
    protected final String relationName;
    protected final Map<Classifier, String> classifiers;
    protected final List<TweetFunction> tweetFunctions;
    protected final List<AccountFunction> accountFunctions;
    protected final List<FeatureSelection> featureSelections;

    public Test(Configuration configuration, String relationName, Map<Classifier, String> classifiers,
                List<TweetFunction> tweetFunctions, List<AccountFunction> accountFunctions, List<FeatureSelection> featureSelections) {
        this.configuration = configuration;
        this.relationName = relationName;
        this.classifiers = classifiers;
        this.tweetFunctions = tweetFunctions;
        this.accountFunctions = accountFunctions;
        this.featureSelections = featureSelections;
    }

    public List<Statistic> test(int foldCount, boolean useExistArffFile) throws Exception {
        if (!useExistArffFile) {
            DatabaseToArff.write(configuration, relationName, tweetFunctions, accountFunctions);
        }

        BufferedReader datafile = new BufferedReader(new FileReader(relationName + ".arff"));
        Instances instances = new Instances(datafile);
        instances.setClassIndex(instances.numAttributes() - 1);

        double currentIteration = 0.;
        double iterationNumber = featureSelections.size();
        List<Statistic> statistics = new ArrayList<>();
        for (FeatureSelection featureSelection : featureSelections) {
            long time = System.currentTimeMillis();
            Instances newInstances = featureSelection.select(instances);
            for (Classifier classifier : classifiers.keySet()) {
                statistics.add(test(newInstances, foldCount, classifier, featureSelection.toString()));
            }
            currentIteration++;
            System.out.format("%.2f%% %s (%d)\n",
                    currentIteration / iterationNumber * 100, featureSelection.toString(), System.currentTimeMillis() - time);
        }
        return statistics;
    }

    protected abstract Statistic test(Instances instances, int foldCount, Classifier classifier, String featureSelectionName) throws Exception;
}
