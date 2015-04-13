package com.samborskiy.tests;

import com.samborskiy.algorithm.AdaBoostClassifier;
import com.samborskiy.algorithm.Classifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.AccountWithTweet;
import com.samborskiy.entity.instances.Tweet;
import com.samborskiy.entity.instances.TweetModifier;
import com.samborskiy.statistic.Statistics;
import com.samborskiy.statistic.TestMachine;

import java.util.List;

public class AdaBoostTest implements Test {

    private final List<Classifier<Account>> classifiers;
    private final List<Double> weights;
    private final List<TweetModifier> modifiers;

    public AdaBoostTest(List<Classifier<Account>> classifiers, List<Double> weights, List<TweetModifier> modifiers) {
        this.classifiers = classifiers;
        this.weights = weights;
        this.modifiers = modifiers;
    }

    @Override
    public String getName() {
        return "AdaBoost";
    }

    @Override
    public Statistics crossValidationAccount(Configuration configuration, int foldCount, int round, List<Account> sample) {
        AdaBoostClassifier<Account> adaBoostClassifier = new AdaBoostClassifier<>(configuration.getLang(), classifiers, weights, modifiers);
        TestMachine<Account> machine = new TestMachine<>(adaBoostClassifier, sample, configuration.getTypes().size());
        return machine.crossValidationTest(foldCount, round, true);
    }

    @Override
    public Statistics crossValidationTweet(Configuration configuration, int foldCount, int round, List<Tweet> sample) {
        throw new UnsupportedOperationException("Method not realised");
    }

    @Override
    public Statistics crossValidationAccountByTweet(Configuration configuration, int foldCount, int round, List<AccountWithTweet> sample) {
        throw new UnsupportedOperationException("Method not realised");
    }
}
