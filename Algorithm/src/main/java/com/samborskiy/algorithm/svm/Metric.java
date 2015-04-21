package com.samborskiy.algorithm.svm;

import com.samborskiy.entity.instances.string.Instance;

import java.util.List;

public interface Metric<E extends Instance> {

    public List<Double> getAttrs(E instance);
}
