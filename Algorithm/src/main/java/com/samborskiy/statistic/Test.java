package com.samborskiy.statistic;

import com.samborskiy.classifiers.ClassifierWrapper;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.functions.AccountFunction;
import com.samborskiy.feature.Feature;
import com.samborskiy.weka.DatabaseToArff;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 01.05.2015.
 */
public abstract class Test {

    protected final Configuration configuration;
    protected final String relationName;
    protected final List<ClassifierWrapper> classifierWrappers;
    protected final List<AccountFunction> accountFunctions;
    protected final List<Feature> features;

    public Test(Configuration configuration, String relationName, List<ClassifierWrapper> classifierWrappers,
                List<AccountFunction> accountFunctions, List<Feature> features) {
        this.configuration = configuration;
        this.relationName = relationName;
        this.classifierWrappers = classifierWrappers;
        this.accountFunctions = accountFunctions;
        this.features = features;
    }

    public List<Statistic> test(int foldCount, boolean useExistArffFile) throws Exception {
        if (!useExistArffFile) {
            DatabaseToArff.write(configuration, relationName, accountFunctions);
        }

        BufferedReader datafile = new BufferedReader(new FileReader(relationName + ".arff"));
        Instances instances = new Instances(datafile);
        instances.setClassIndex(instances.numAttributes() - 1);

        double currentIteration = 0.;
        double iterationNumber = features.size();
        List<Statistic> statistics = new ArrayList<>();
        for (Feature feature : features) {
            long time = System.currentTimeMillis();
            Instances newInstances = feature.select(instances);
            for (ClassifierWrapper classifierWrapper : classifierWrappers) {
                Statistic statistic = test(newInstances, foldCount, classifierWrapper, feature.toString());
                statistics.add(statistic);
            }
            currentIteration++;
            System.out.format("%.2f%% %s (%d)\n",
                    currentIteration / iterationNumber * 100, feature.toString(), System.currentTimeMillis() - time);
        }
        return statistics;
    }

    protected abstract Statistic test(Instances instances, int foldCount, ClassifierWrapper classifierWrapper, String featureSelectionName) throws Exception;
}
