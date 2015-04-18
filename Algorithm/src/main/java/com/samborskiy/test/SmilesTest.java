package com.samborskiy.test;

import com.samborskiy.algorithm.Classifier;
import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Instance;
import com.samborskiy.entity.instances.Modifier;
import com.samborskiy.entity.instances.ModifierSmiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 18.04.2015.
 */
public class SmilesTest extends Test {

    public SmilesTest(Configuration configuration, int rounds, int folds) {
        super(configuration, rounds, folds);
    }

    @Override
    protected List<Modifier> getModifiers() {
        List<Modifier> modifiers = new ArrayList<>();
        modifiers.add(new ModifierSmiles());
        return modifiers;
    }

    @Override
    protected <E extends Instance> Classifier<E> getClassifier() {
        return new NaiveBayesClassifier<>(configuration.getLang());
    }
}
