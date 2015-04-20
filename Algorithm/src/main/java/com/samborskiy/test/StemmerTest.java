package com.samborskiy.test;

import com.samborskiy.algorithm.Classifier;
import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Instance;
import com.samborskiy.entity.instances.modifiers.Modifier;
import com.samborskiy.entity.instances.modifiers.ModifierStemmer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 18.04.2015.
 */
public class StemmerTest extends Test {

    public StemmerTest(Configuration configuration, int rounds, int folds) {
        super(configuration, rounds, folds);
    }

    @Override
    protected List<Modifier> getModifiers() {
        List<Modifier> modifiers = new ArrayList<>();
        modifiers.add(new ModifierStemmer());
        return modifiers;
    }

    @Override
    protected <E extends Instance> Classifier<E> getClassifier() {
        return new NaiveBayesClassifier<>(configuration.getLang());
    }
}
