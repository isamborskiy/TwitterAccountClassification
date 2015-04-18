package com.samborskiy.test;

import com.samborskiy.algorithm.BaggingClassifier;
import com.samborskiy.algorithm.Classifier;
import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Instance;
import com.samborskiy.entity.instances.Modifier;
import com.samborskiy.entity.instances.ModifierLength;
import com.samborskiy.entity.instances.ModifierSimple;
import com.samborskiy.entity.instances.ModifierSmiles;
import com.samborskiy.entity.instances.ModifierStemmer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 18.04.2015.
 */
public class BaggingTest extends Test {

    public BaggingTest(Configuration configuration, int rounds, int folds) {
        super(configuration, rounds, folds);
    }

    @Override
    protected List<Modifier> getModifiers() {
        List<Modifier> modifiers = new ArrayList<>();
        modifiers.add(new ModifierSimple());
        modifiers.add(new ModifierStemmer());
        modifiers.add(new ModifierSmiles());
        modifiers.add(new ModifierLength());
        return modifiers;
    }

    @Override
    protected <E extends Instance> Classifier<E> getClassifier() {
        List<Classifier<E>> classifiers = new ArrayList<>();
        classifiers.add(new NaiveBayesClassifier<>(configuration.getLang()));
        classifiers.add(new NaiveBayesClassifier<>(configuration.getLang()));
        classifiers.add(new NaiveBayesClassifier<>(configuration.getLang()));
        classifiers.add(new NaiveBayesClassifier<>(configuration.getLang()));
        return new BaggingClassifier<E>(configuration.getLang(), classifiers);
    }
}
