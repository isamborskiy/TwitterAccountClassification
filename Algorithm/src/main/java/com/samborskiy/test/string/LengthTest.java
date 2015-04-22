package com.samborskiy.test.string;

import com.samborskiy.algorithm.string.Classifier;
import com.samborskiy.algorithm.string.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.string.Instance;
import com.samborskiy.entity.instances.modifiers.Modifier;
import com.samborskiy.entity.instances.modifiers.ModifierLength;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 18.04.2015.
 */
public class LengthTest extends Test {

    public LengthTest(Configuration configuration, int rounds, int folds) {
        super(configuration, rounds, folds);
    }

    @Override
    protected List<Modifier> getModifiers() {
        List<Modifier> modifiers = new ArrayList<>();
        modifiers.add(new ModifierLength());
        return modifiers;
    }

    @Override
    protected <E extends Instance> Classifier<E> getClassifier() {
        return new NaiveBayesClassifier<>(configuration.getLang());
    }
}
