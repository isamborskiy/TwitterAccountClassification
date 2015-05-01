package com.samborskiy.statistic;

/**
 * Created by Whiplash on 01.05.2015.
 */
public class Statistic implements Comparable<Statistic> {

    private final double fMeasure;
    private final double accuracy;
    private final String featureSelectionName;
    private final String classifierName;
    private final int attributeNumber;

    public Statistic(double fMeasure, double accuracy, String featureSelectionName, String classifierName, int attributeNumber) {
        this.fMeasure = fMeasure;
        this.accuracy = accuracy;
        this.featureSelectionName = featureSelectionName;
        this.classifierName = classifierName;
        this.attributeNumber = attributeNumber;
    }

    public double getFMeasure() {
        return fMeasure;
    }

    @Override
    public int compareTo(Statistic o) {
        return Double.compare(getFMeasure(), o.getFMeasure());
    }

    @Override
    public String toString() {
        return String.format("Classifier %s with feature subset selection algorithm %s(%d):\nAccuracy: %f\nF-measure: %f\n",
                classifierName, featureSelectionName, attributeNumber, accuracy, fMeasure);
    }
}
