package com.samborskiy.algorithm.string.svm;

import com.samborskiy.entity.instances.string.Instance;

import java.util.List;

import static java.lang.StrictMath.exp;
import static java.lang.StrictMath.pow;

public class GaussKernel<E extends Instance> extends Kernel<E> {

    private double gamma;

    public GaussKernel(Metric<E> metric, double gamma) {
        super(metric);
        this.gamma = gamma;
    }

    @Override
    public double eval(E first, E second) {
        List<Double> firstAttrs = metric.getAttrs(first);
        List<Double> secondAttrs = metric.getAttrs(second);
        return exp(-gamma * pow(euclideanDistance(firstAttrs, secondAttrs), 2));
    }

    private double euclideanDistance(List<Double> firstAttrs, List<Double> secondAttrs) {
        double result = 0;
        for (int i = 0; i < firstAttrs.size(); i++) {
            result += StrictMath.pow(firstAttrs.get(i) - secondAttrs.get(i), 2);
        }
        return StrictMath.sqrt(result);
    }
}
