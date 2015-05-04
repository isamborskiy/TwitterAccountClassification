package com.samborskiy;

import com.samborskiy.classifiers.ClassifierWrapper;
import com.samborskiy.classifiers.RandomForestVariation;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.functions.AttributeFunction;
import com.samborskiy.feature.Feature;
import com.samborskiy.statistic.Statistic;
import com.samborskiy.statistic.Test;
import com.samborskiy.statistic.WekaTest;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    //        private static final String TEST_FILE_PATH = "res/ru/old_config.json";
    private static final int FOLD_COUNT = 5;
    private static final String RELATION_NAME = "train";

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        Test test = new WekaTest(configuration, RELATION_NAME, getClassifierWrappers(), getTweetAttributes(), getFeatures());
        List<Statistic> statistics = test.test(FOLD_COUNT, true);
        Collections.sort(statistics);
        statistics.forEach(System.out::println);
    }

    private static List<ClassifierWrapper> getClassifierWrappers() throws Exception {
        List<ClassifierWrapper> wrappers = new ArrayList<>();
        wrappers.addAll(new RandomForestVariation().getClassifiers());
//        Map<Classifier, String> classifiers = new HashMap<>();
//        classifiers.put(new LibSVM(), "SVM");
//        for (int i = 1; i <= 30; i++) {
//            classifiers.put(new IBk(i), "KNN" + i);
//        }
//        classifiers.put(new BayesNet(), "Bayes network");
//        classifiers.put(new J48(), "Decision Tree (J48)");
//        classifiers.put(new NaiveBayes(), "Naive Bayes");
//        classifiers.put(new PART(), "Rule-based PART");
//        classifiers.put(new RandomForest(), "Random forest");
        return wrappers;
    }

    private static List<Feature> getFeatures() throws InstantiationException, IllegalAccessException {
        List<Feature> featureSelections = new ArrayList<>();
        featureSelections.addAll(getFeatures("com.samborskiy.feature.selection"));
        featureSelections.addAll(getFeatures("com.samborskiy.feature.extraction"));
        return featureSelections;
    }

    private static List<Feature> getFeatures(String packageName) throws IllegalAccessException, InstantiationException {
        return getClasses(packageName, Feature.class);
    }

    private static List<AttributeFunction> getTweetAttributes() throws InstantiationException, IllegalAccessException {
        List<AttributeFunction> attributeFunctions = new ArrayList<>();
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.partofspeech"));
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.sign"));
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.smile"));
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.length"));
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.grammar"));
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.vocabulary"));
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.hashtag"));
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.reference"));
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.personal"));
        attributeFunctions.addAll(getTweetAttributes("com.samborskiy.entity.instances.functions.frequency"));
        return attributeFunctions;
    }

    private static List<AttributeFunction> getTweetAttributes(String packageName) throws IllegalAccessException, InstantiationException {
        return getClasses(packageName, AttributeFunction.class);
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
