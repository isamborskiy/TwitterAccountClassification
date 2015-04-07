package com.samborskiy.tests;

import com.samborskiy.algorithm.NaiveBayesClassifierStemmer;
import com.samborskiy.entity.Account;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;
import com.samborskiy.statistic.Statistics;
import com.samborskiy.statistic.TestMachine;

import java.util.List;

public class NaiveBayesStemmerTest implements Test {

    @Override
    public String getName() {
        return "Naive Bayes (stemmer)";
    }

    @Override
    public Statistics crossValidationAccount(Configuration configuration, int foldCount, int round, List<Account> sample) {
        TestMachine<Account> machine = new TestMachine<>(new NaiveBayesClassifierStemmer<>(configuration.getLang()), sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, false);
    }

    @Override
    public Statistics crossValidationTweet(Configuration configuration, int foldCount, int round, List<Tweet> sample) {
        TestMachine<Tweet> machine = new TestMachine<>(new NaiveBayesClassifierStemmer<>(configuration.getLang()), sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, false);
    }
}
