package com.samborskiy;

import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Account;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;
import com.samborskiy.statistic.Statistics;
import com.samborskiy.statistic.TestMachine;

import java.util.List;

/**
 * Created by Whiplash on 06.04.2015.
 */
public class NaiveBayesAccountTest implements Test {

    @Override
    public String getName() {
        return "Naive Bayes";
    }

    @Override
    public Statistics crossValidationAccount(Configuration configuration, int foldCount, int round, List<Account> sample) {
        TestMachine<Account> machine = new TestMachine<>(new NaiveBayesClassifier<>(), sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, false);
    }

    @Override
    public Statistics crossValidationTweet(Configuration configuration, int foldCount, int round, List<Tweet> sample) {
        TestMachine<Tweet> machine = new TestMachine<>(new NaiveBayesClassifier<>(), sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, false);
    }
}
