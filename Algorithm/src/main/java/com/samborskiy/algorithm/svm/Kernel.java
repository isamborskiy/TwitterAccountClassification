package com.samborskiy.algorithm.svm;

import com.samborskiy.entity.instances.Instance;

/**
 * Created by Whiplash on 19.10.2014.
 */
public abstract class Kernel<E extends Instance> {

    protected final Metric<E> metric;

    public Kernel(Metric<E> metric) {
        this.metric = metric;
    }

    public abstract double eval(E first, E second);
}
