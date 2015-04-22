package com.samborskiy.algorithm.string.svm;

import com.samborskiy.entity.instances.string.Instance;

import java.util.List;

public class InnerProductKernel<E extends Instance> extends Kernel<E> {

    public InnerProductKernel(Metric<E> metric) {
        super(metric);
    }

    @Override
    public double eval(E first, E second) {
        List<Double> firstAttrs = metric.getAttrs(first);
        List<Double> secondAttrs = metric.getAttrs(second);
        double result = 0;
        for (int i = 0; i < firstAttrs.size(); i++) {
            result += firstAttrs.get(i) * secondAttrs.get(i);
        }
        return result;
    }
}
