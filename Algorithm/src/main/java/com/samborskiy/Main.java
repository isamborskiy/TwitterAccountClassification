package com.samborskiy;

import com.samborskiy.classifiers.ClassifierWrapper;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.functions.AccountFunction;
import com.samborskiy.feature.Feature;
import com.samborskiy.feature.NoFeatureSelection;
import com.samborskiy.statistic.ConfusionMatrixTest2;
import com.samborskiy.statistic.Statistic;
import com.samborskiy.statistic.Test;
import org.reflections.Reflections;
import weka.classifiers.trees.RandomForest;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/en/config.json";
    private static final int FOLD_COUNT = 5;
    private static final String RELATION_NAME = "train";

    public static void main(String[] args) throws Exception {
        List<Statistic> statistics = getStatistics();
        Collections.sort(statistics);
        statistics.forEach(System.out::println);
    }

    public static List<Statistic> getStatistics() throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        Test test = new ConfusionMatrixTest2(configuration, RELATION_NAME, getClassifierWrappers(), getTweetAttributes(), getFeatures());
        return test.test(FOLD_COUNT, true);
//        InfoGainAttributeEval eval = new InfoGainAttributeEval().buildEvaluator(null);
//        eval.evaluateAttribute()
    }

    public static List<ClassifierWrapper> getClassifierWrappers() throws Exception {
        List<ClassifierWrapper> wrappers = new ArrayList<>();
//        wrappers.addAll(new RandomForestVariation().getClassifiers());

//        wrappers.add(new ClassifierWrapper(new IBk()));
//        wrappers.add(new ClassifierWrapper(new NaiveBayes()));
//        wrappers.add(new ClassifierWrapper(new LibSVM()));
//        wrappers.add(new ClassifierWrapper(new J48()));
        wrappers.add(new ClassifierWrapper(new RandomForest()));
//        RandomForest randomForest = new RandomForest();
//        randomForest.setOptions(new String[]{"-I", "105", "-K", "4"});
//        wrappers.add(new ClassifierWrapper(randomForest));

        return wrappers;
    }

    public static List<Feature> getFeatures() throws InstantiationException, IllegalAccessException {
        List<Feature> featureSelections = new ArrayList<>();
//        featureSelections.add(new CFS_BiS());
        featureSelections.add(new NoFeatureSelection());
        featureSelections.addAll(getFeatures("com.samborskiy.feature.selection"));
        featureSelections.addAll(getFeatures("com.samborskiy.feature.extraction"));
        return featureSelections;
    }

    private static List<Feature> getFeatures(String packageName) throws IllegalAccessException, InstantiationException {
        return getClasses(packageName, Feature.class);
    }

    public static List<AccountFunction> getTweetAttributes() throws InstantiationException, IllegalAccessException {
        List<AccountFunction> accountFunctions = new ArrayList<>();
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.partofspeech"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.sign"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.smile"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.length"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.grammar"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.vocabulary"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.hashtag"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.reference"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.personal"));
//        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.frequency"));
        return accountFunctions;
    }

    private static List<AccountFunction> getTweetAttributes(String packageName) throws IllegalAccessException, InstantiationException {
        return getClasses(packageName, AccountFunction.class);
    }

    private static <E> List<E> getClasses(String packageName, Class<E> type) throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends E>> allClasses = reflections.getSubTypesOf(type);
        List<E> classes = new ArrayList<>();
        for (Class clazz : allClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                classes.add((E) clazz.newInstance());
            }
        }
        return classes;
    }
}
