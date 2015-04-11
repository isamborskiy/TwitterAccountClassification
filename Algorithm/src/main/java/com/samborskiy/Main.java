package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.AccountWithTweet;
import com.samborskiy.entity.instances.Tweet;
import com.samborskiy.entity.instances.WordModifier;
import com.samborskiy.entity.instances.WordModifierStemmer;
import com.samborskiy.misc.InstancesFromDatabase;
import com.samborskiy.tests.NaiveBayesTest;
import com.samborskiy.tests.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;
    private static final int ROUNDS = 50;

    private static final List<Test> testes = new ArrayList<>();

    static {
        testes.add(new NaiveBayesTest());
    }

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        WordModifier modifier = new WordModifierStemmer();
        List<Account> accounts = InstancesFromDatabase.getAllAccounts(configuration, modifier);
        List<Tweet> tweets = InstancesFromDatabase.getAllSimpleTweets(configuration, modifier);
        List<AccountWithTweet> accountsWithTweet = InstancesFromDatabase.getAllAccountsWithTweet(configuration, modifier);

        for (Test test : testes) {
            System.out.format("%s\n", test.getName());
            System.out.format("Accounts:\n%s\n", test.crossValidationAccount(configuration, FOLD_COUNT, ROUNDS, accounts));
            System.out.format("Tweets:\n%s\n", test.crossValidationTweet(configuration, FOLD_COUNT, ROUNDS, tweets));
            System.out.format("Account by tweet:\n%s\n", test.crossValidationAccountByTweet(configuration, FOLD_COUNT, ROUNDS, accountsWithTweet));
        }
    }
}
