package com.samborskiy;

import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Account;
import com.samborskiy.entity.Configuration;
import com.samborskiy.misc.InstancesFromDatabase;
import com.samborskiy.statistic.Statistics;
import com.samborskiy.statistic.TestMachine;

import java.io.File;
import java.util.List;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;
    private static final int ROUNDS = 50;

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        List<Account> sample = InstancesFromDatabase.getAllAccounts(configuration);
        TestMachine<Account> machine = new TestMachine<>(new NaiveBayesClassifier<>(), sample, configuration.getTypes().size());
        Statistics statistics = machine.crossValidationTest(FOLD_COUNT, ROUNDS, false);
        System.out.println(statistics.getFMeasure());
    }

}
