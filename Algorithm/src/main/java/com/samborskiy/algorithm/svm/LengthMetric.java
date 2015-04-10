package com.samborskiy.algorithm.svm;

import com.samborskiy.entity.instances.Instance;

import java.util.ArrayList;
import java.util.List;

public class LengthMetric<E extends Instance> implements Metric<E> {

    @Override
    public List<Double> getAttrs(E instance) {
        List<Double> attr = new ArrayList<>(1);
        attr.add((double) instance.size());
        return attr;
    }
}
