package com.samborskiy;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Whiplash on 17.05.2015.
 */
public class InformationGain {

    public static void main(String[] args) throws Exception {
        Set<Map.Entry<String, Double>> sortedStatistics = getStatistics();
        for (Map.Entry<String, Double> statistic : sortedStatistics) {
            System.out.format("%s is %f\n", statistic.getKey(), statistic.getValue());
        }
    }

    public static Set<Map.Entry<String, Double>> getStatistics() throws Exception {
        BufferedReader datafile = new BufferedReader(new FileReader("train.arff"));
        Instances instances = new Instances(datafile);
        instances.setClassIndex(instances.numAttributes() - 1);
        instances.deleteAttributeAt(instances.attribute("retweet_number").index());

        InfoGainAttributeEval eval = new InfoGainAttributeEval();
        eval.buildEvaluator(instances);
        Map<String, Double> iGains = new HashMap<>();
        for (int i = 0; i < instances.numAttributes() - 1; i++) {
            String name = instances.attribute(i).name();
            double iGain = eval.evaluateAttribute(i);
            iGains.put(name, iGain);
        }
        return entriesSortedByValues(iGains);
    }

    private static <K, V extends Comparable<? super V>> Set<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        Set<Map.Entry<K, V>> sortedEntries = new TreeSet<>(
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

}
