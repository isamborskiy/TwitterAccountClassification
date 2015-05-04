package com.samborskiy.classifiers;

import weka.classifiers.Classifier;
import weka.core.OptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Whiplash on 03.05.2015.
 */
public abstract class ClassifierVariation {

    protected final List<String> paramsName = new ArrayList<>();
    protected final Map<String, List<String>> params = new HashMap<>();

    public List<ClassifierWrapper> getClassifiers() throws Exception {
        List<ClassifierWrapper> wrappers = new ArrayList<>();
        int[] vector = new int[paramsName.size()];
        int iterationNumber = params.values().stream().mapToInt(List::size).sum();
        for (int i = 0; i < iterationNumber; i++) {
            Classifier classifier = getClassifier();
            ((OptionHandler) classifier).setOptions(generateParam(vector));
            wrappers.add(new ClassifierWrapper(classifier));
            inc(vector, 0);
        }
        return wrappers;
    }

    protected void inc(int[] vector, int index) {
        if (index == vector.length) {
            return;
        }
        List<String> paramValues = params.get(paramsName.get(index));
        if (paramValues == null || paramValues.isEmpty()) {
            inc(vector, index + 1);
            return;
        }
        vector[index]++;
        vector[index] %= paramValues.size();
        if (vector[index] == 0) {
            inc(vector, index + 1);
        }
    }

    protected abstract Classifier getClassifier();

    protected String[] generateParam(int[] vector) {
        List<String> param = new ArrayList<>();
        for (int i = 0; i < vector.length; i++) {
            String paramName = paramsName.get(i);
            int index = vector[i];
            param.add(paramName);
            String value = getParamValue(paramName, index);
            if (value != null) {
                param.add(value);
            }
        }
        return param.toArray(new String[param.size()]);
    }

    protected String getParamValue(String paramName, int index) {
        List<String> paramValues = params.get(paramName);
        if (paramValues == null || paramValues.isEmpty()) {
            return null;
        } else {
            return paramValues.get(index);
        }
    }
}
