package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.functions.account.AccountFunction;
import com.samborskiy.entity.instances.functions.tweet.TweetFunction;
import org.reflections.Reflections;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;
    private static final String RELATION_NAME = "test_file";

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        Test test = new Test(configuration);
        test.test(RELATION_NAME, FOLD_COUNT, getClassifiers(), getTweetAttributes(), getAccountFunctions());
    }

    private static Map<Classifier, String> getClassifiers() {
        Map<Classifier, String> classifiers = new HashMap<>();
//        classifiers.put(new LibSVM(), "SVM");
        classifiers.put(new IBk(), "KNN");
        classifiers.put(new J48(), "Decision Tree (J48)");
        classifiers.put(new NaiveBayes(), "Naive Bayes");
        return classifiers;
    }

    public static List<AccountFunction> getAccountFunctions() {
        List<AccountFunction> accountFunctions = new ArrayList<>();
//        accountFunctions.add(new HashTagAttribute());
        return accountFunctions;
    }

    private static List<TweetFunction> getTweetAttributes() throws InstantiationException, IllegalAccessException {
        List<TweetFunction> tweetFunctions = new ArrayList<>();
        tweetFunctions.addAll(getAttributes("com.samborskiy.entity.instances.functions.tweet.partofspeech"));
        tweetFunctions.addAll(getAttributes("com.samborskiy.entity.instances.functions.tweet.sign"));
        tweetFunctions.addAll(getAttributes("com.samborskiy.entity.instances.functions.tweet.smile"));
        tweetFunctions.addAll(getAttributes("com.samborskiy.entity.instances.functions.tweet.length"));
//        tweetFunctions.addAll(getAttributes("com.samborskiy.entity.instances.functions.tweet.grammar"));
        tweetFunctions.addAll(getAttributes("com.samborskiy.entity.instances.functions.tweet.vocabulary"));
        tweetFunctions.addAll(getAttributes("com.samborskiy.entity.instances.functions.tweet.hashtag"));
        return tweetFunctions;
    }

    private static List<TweetFunction> getAttributes(String packageName) throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends TweetFunction>> allClasses = reflections.getSubTypesOf(TweetFunction.class);
        List<TweetFunction> tweetFunctions = new ArrayList<>();
        for (Class clazz : allClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                tweetFunctions.add((TweetFunction) clazz.newInstance());
            }
        }
        return tweetFunctions;
    }
}
