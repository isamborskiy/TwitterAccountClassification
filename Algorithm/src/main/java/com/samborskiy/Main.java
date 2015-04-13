package com.samborskiy;

import com.samborskiy.algorithm.Classifier;
import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.TweetModifier;
import com.samborskiy.entity.instances.TweetModifierLength;
import com.samborskiy.entity.instances.TweetModifierSmiles;
import com.samborskiy.entity.instances.TweetModifierStemmer;
import com.samborskiy.entity.instances.TweetModifierWithoutModifier;
import com.samborskiy.misc.InstancesFromDatabase;
import com.samborskiy.statistic.Statistics;
import com.samborskiy.tests.AdaBoostTest;
import com.samborskiy.tests.NaiveBayesTest;
import com.samborskiy.tests.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;
    private static final int ROUNDS = 1;

    public static void main(String[] args) throws Exception {
        testAdaBoost();
    }

    private static void testMethod() {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        TweetModifier modifier = new TweetModifierWithoutModifier();
        List<Account> accounts = InstancesFromDatabase.getAllAccounts(configuration, modifier);
        Test test = new NaiveBayesTest();
        System.out.format("Statistics:\n%s\n\n", test.crossValidationAccount(configuration, FOLD_COUNT, ROUNDS, accounts));
    }

    private static void testAdaBoost() {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        TweetModifier modifier = new TweetModifierWithoutModifier();
        List<Account> accounts = InstancesFromDatabase.getAllAccounts(configuration, modifier);
        Collections.shuffle(accounts);
        double maxF = 0.;
        String ijk = "";
        for (double i = 0.; i <= 1; i += 0.1) {
            for (double j = 0.; j <= 1; j += 0.1) {
                for (double k = 0.; k <= 1; k += 0.1) {
                    List<Classifier<Account>> classifiers = new ArrayList<>();
                    List<Double> weights = new ArrayList<>();
                    List<TweetModifier> modifiers = new ArrayList<>();

                    classifiers.add(new NaiveBayesClassifier<>(configuration.getLang()));
                    classifiers.add(new NaiveBayesClassifier<>(configuration.getLang()));
                    classifiers.add(new NaiveBayesClassifier<>(configuration.getLang()));

                    weights.add(0.7);
                    weights.add(0.5);
                    weights.add(0.6);

                    modifiers.add(new TweetModifierStemmer());
                    modifiers.add(new TweetModifierSmiles());
                    modifiers.add(new TweetModifierLength());

                    Test test = new AdaBoostTest(classifiers, weights, modifiers);

                    System.out.format("%f -- %f -- %f\n", i, j, k);
                    Statistics statistics = test.crossValidationAccount(configuration, FOLD_COUNT, ROUNDS, accounts);
                    System.out.format("Accounts:\n%s\n\n", statistics);
                    if (maxF < statistics.getFMeasure()) {
                        maxF = statistics.getFMeasure();
                        ijk = String.format("%f -- %f -- %f\n", i, j, k);
                    }
                }
            }
        }
        System.out.println("Max F-measure is " + maxF + " while ijk = " + ijk);
    }
}
