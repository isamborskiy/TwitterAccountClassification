package com.samborskiy.algorithm.attrs.knn;

/**
 * Interface class that has the following methods:
 * {@link #eval(double)}
 * <p>
 * Created by Whiplash on 21.09.2014.
 */
public interface Kernel {

    /**
     * Measures weight between {@code first} and {@code second}
     *
     * @param value argument of kernel function
     * @return calculated weight
     */
    public double eval(double value);

}
