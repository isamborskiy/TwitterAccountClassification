package com.samborskiy.test;

import java.util.Map;

/**
 * Created by Whiplash on 24.04.2015.
 */
public class Statistics {

    private final Test test;

    public Statistics(Test test) {
        this.test = test;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Accuracy: %f\n", test.getAccuracy()));
        builder.append(String.format("F-measure: %f\n", test.getFMeasure()));
        Map<Integer, Double> map = test.getFMeasures();
        for (Integer classId : map.keySet()) {
            builder.append(String.format("F-measure of %d class is %f\n", classId, map.get(classId)));
        }
        return builder.toString();
    }
}
