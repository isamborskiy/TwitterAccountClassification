package com.samborskiy.tests;

import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.AccountWithTweet;
import com.samborskiy.entity.instances.Tweet;
import com.samborskiy.statistic.Statistics;
import com.samborskiy.statistic.TestMachine;

import java.util.List;

public class NaiveBayesTest /*implements Test*/ {

    /*@Override
    public String getName() {
        return "Naive Bayes (simple)";
    }

    @Override
    public Statistics crossValidationAccount(Configuration configuration, int foldCount, int round, List<Account> sample) {
        TestMachine<Account> machine = new TestMachine<>(new NaiveBayesClassifier<>(configuration.getLang()), sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, false);
    }

    @Override
    public Statistics crossValidationTweet(Configuration configuration, int foldCount, int round, List<Tweet> sample) {
        TestMachine<Tweet> machine = new TestMachine<>(new NaiveBayesClassifier<>(configuration.getLang()), sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, false);
    }

    @Override
    public Statistics crossValidationAccountByTweet(Configuration configuration, int foldCount, int round, List<AccountWithTweet> sample) {
        TestMachine<AccountWithTweet> machine = new TestMachine<>(new NaiveBayesClassifier<>(configuration.getLang()), sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, false);
    }*/
}
