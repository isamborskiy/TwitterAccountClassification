package com.samborskiy;

import com.samborskiy.entity.Account;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;
import com.samborskiy.misc.InstancesFromDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;
    private static final int ROUNDS = 1;

    private static final List<Test> testes = new ArrayList<>();

    static {
        testes.add(new NaiveBayesAccountTest());
    }

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        List<Account> accounts = InstancesFromDatabase.getAllAccounts(configuration);
        List<Tweet> tweets = InstancesFromDatabase.getAllTweets(configuration);

        for (Test test : testes) {
            System.out.format("%s\nAccounts:\n%s\nTweets:\n%s\n",
                    test.getName(),
                    test.crossValidationAccount(configuration, FOLD_COUNT, ROUNDS, accounts),
                    test.crossValidationTweet(configuration, FOLD_COUNT, ROUNDS, tweets));
        }
    }
}
