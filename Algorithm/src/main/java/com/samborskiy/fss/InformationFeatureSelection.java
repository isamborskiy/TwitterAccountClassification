package com.samborskiy.fss;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.*;

/**
 * Created by whiplash on 04.06.2015.
 */
public class InformationFeatureSelection extends FeatureSelection {

    private final int n;

    public InformationFeatureSelection(int n) {
        this.n = n;
    }

    @Override
    public Instances select(Instances instances) {
        try {
            Instances copiedInstances = new Instances(instances);
            Set<Map.Entry<String, Double>> sortedStatistics = getStatistics(copiedInstances);
            sortedStatistics.stream().filter(entry -> copiedInstances.numAttributes() > n).forEach(entry -> {
                Attribute attribute = copiedInstances.attribute(entry.getKey());
                copiedInstances.deleteAttributeAt(attribute.index());
            });
            return copiedInstances;
        } catch (Exception e) {
            return null;
        }
    }

    private Set<Map.Entry<String, Double>> getStatistics(Instances instances) throws Exception {
        InfoGainAttributeEval infoGainAttributeEval = new InfoGainAttributeEval();
        infoGainAttributeEval.buildEvaluator(instances);
        Map<String, Double> iGains = new HashMap<>();
        for (int i = 0; i < instances.numAttributes() - 1; i++) {
            String name = instances.attribute(i).name();
            double iGain = infoGainAttributeEval.evaluateAttribute(i);
            iGains.put(name, iGain);
        }
        return entriesSortedByValues(iGains);
    }

    private <K, V extends Comparable<? super V>> Set<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
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

    @Override
    protected ASSearch getSearcher() {
        return null;
    }

    @Override
    protected ASEvaluation getEvaluator() {
        return null;
    }
}
