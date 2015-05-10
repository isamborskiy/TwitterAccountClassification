package com.samborskiy.classifiers;

import weka.classifiers.Classifier;
import weka.core.OptionHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 03.05.2015.
 */
public class ClassifierWrapper {

    private final Classifier classifier;

    public ClassifierWrapper(Classifier classifier) {
        this.classifier = classifier;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public String getName() {
        return classifier.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        if (classifier instanceof OptionHandler) {
            return Arrays.stream(((OptionHandler) classifier).getOptions())
                    .collect(Collectors.joining(" ", classifier.getClass().getSimpleName() + " [", "]"));
        } else {
            return classifier.getClass().getSimpleName() + " without params";
        }
    }
}
