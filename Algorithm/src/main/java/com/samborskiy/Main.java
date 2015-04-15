package com.samborskiy;

import com.samborskiy.algorithm.AdaBoostClassifier;
import com.samborskiy.algorithm.Classifier;
import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.TweetModifier;
import com.samborskiy.entity.instances.TweetModifierLength;
import com.samborskiy.entity.instances.TweetModifierSimple;
import com.samborskiy.entity.instances.TweetModifierSmiles;
import com.samborskiy.entity.instances.TweetModifierStemmer;
import com.samborskiy.misc.InstancesFromDatabase;
import com.samborskiy.statistic.TestMachine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;
    private static final int ROUNDS = 50;

    public static void main(String[] args) throws Exception {
        testAdaBoost();
    }

    private static void testMethod() {
//        File configFileTrain = new File(TRAIN_FILE_PATH);
//        Configuration configuration = Configuration.build(configFileTrain);
//        TweetModifier modifier = new TweetModifierWithoutModifier();
//        List<Account> accounts = InstancesFromDatabase.getAllAccounts(configuration, modifier);
//        Test test = new NaiveBayesTest();
//        System.out.format("Statistics:\n%s\n\n", test.crossValidationAccount(configuration, FOLD_COUNT, ROUNDS, accounts));
    }

    private static void testAdaBoost() {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        int n = 4;
        int k = 3;

        List<Classifier<Account>> classifiers = new ArrayList<>();
        List<TweetModifier> modifiers = new ArrayList<>();
        modifiers.add(new TweetModifierSimple());
        modifiers.add(new TweetModifierStemmer());
        modifiers.add(new TweetModifierSmiles());
        modifiers.add(new TweetModifierLength());

        List<List<Account>> accounts = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            TweetModifier modifier = modifiers.get(i);
            List<Account> accountList = InstancesFromDatabase.getAllAccounts(configuration, modifier);
            for (int j = 0; j < k; j++) {
                accounts.add(accountList);
                classifiers.add(new NaiveBayesClassifier<>(configuration.getLang()));
            }
        }
        AdaBoostClassifier<Account> classifier = new AdaBoostClassifier<>(configuration.getLang(), classifiers);
        TestMachine<Account> testMachine = new TestMachine<>(classifier, 3, accounts);
        System.out.println("BEGIN");
        System.out.println(testMachine.crossValidationTest(FOLD_COUNT, ROUNDS, false));
    }
}
