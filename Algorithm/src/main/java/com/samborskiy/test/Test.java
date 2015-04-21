package com.samborskiy.test;

import com.samborskiy.algorithm.Classifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.string.Account;
import com.samborskiy.entity.instances.string.Instance;
import com.samborskiy.entity.instances.modifiers.Modifier;
import com.samborskiy.entity.instances.string.Tweet;
import com.samborskiy.misc.InstancesFromDatabase;
import com.samborskiy.statistic.Statistics;
import com.samborskiy.statistic.TestMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 18.04.2015.
 */
public abstract class Test {

    protected final Configuration configuration;
    protected final int rounds;
    protected final int folds;

    public Test(Configuration configuration, int rounds, int folds) {
        this.configuration = configuration;
        this.rounds = rounds;
        this.folds = folds;
    }

    /**
     * Tests classifier by account entity.
     *
     * @return statistics of classifier test
     */
    public Statistics testAccounts(boolean isParallel) {
        List<Modifier> modifiers = getModifiers();
        Classifier<Account> classifier = getClassifier();
        List<List<Account>> instances = getAccountLists(modifiers);
        TestMachine<Account> testMachine = new TestMachine<>(classifier, configuration.getTypes().size(), instances);
        return testMachine.crossValidationTest(folds, rounds, isParallel);
    }

    /**
     * Tests classifier by tweet entity.
     *
     * @return statistics of classifier test
     */
    public Statistics testTweets(boolean isParallel) {
        List<Modifier> modifiers = getModifiers();
        Classifier<Tweet> classifier = getClassifier();
        List<List<Tweet>> instances = getTweetLists(modifiers);
        TestMachine<Tweet> testMachine = new TestMachine<>(classifier, configuration.getTypes().size(), instances);
        return testMachine.crossValidationTest(folds, rounds, isParallel);
    }

    protected abstract List<Modifier> getModifiers();

    protected abstract <E extends Instance> Classifier<E> getClassifier();

    protected List<List<Account>> getAccountLists(List<Modifier> modifiers) {
        List<List<Account>> instances = new ArrayList<>();
        for (Modifier modifier : modifiers) {
            List<Account> accounts = InstancesFromDatabase.getAllAccounts(configuration, modifier);
            instances.add(accounts);
        }
        return instances;
    }

    protected List<List<Tweet>> getTweetLists(List<Modifier> modifiers) {
        List<List<Tweet>> instances = new ArrayList<>();
        for (Modifier modifier : modifiers) {
            List<Tweet> tweets = InstancesFromDatabase.getAllTweets(configuration, modifier);
            instances.add(tweets);
        }
        return instances;
    }
}
