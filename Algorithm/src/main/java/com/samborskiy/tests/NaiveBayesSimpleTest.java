package com.samborskiy.tests;

import com.samborskiy.algorithm.NaiveBayesClassifierSimple;
import com.samborskiy.entity.Account;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;
import com.samborskiy.statistic.Statistics;
import com.samborskiy.statistic.TestMachine;

import java.util.List;

public class NaiveBayesSimpleTest implements Test {

    @Override
    public String getName() {
        return "Naive Bayes (simple)";
    }

    @Override
    public Statistics crossValidationAccount(Configuration configuration, int foldCount, int round, List<Account> sample) {
        TestMachine<Account> machine = new TestMachine<>(new NaiveBayesClassifierSimple<>(configuration.getLang()), sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, false);
    }

    @Override
    public Statistics crossValidationTweet(Configuration configuration, int foldCount, int round, List<Tweet> sample) {
        TestMachine<Tweet> machine = new TestMachine<>(new NaiveBayesClassifierSimple<>(configuration.getLang()), sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, false);
    }
}
