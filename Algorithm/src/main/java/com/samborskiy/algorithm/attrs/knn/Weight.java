package com.samborskiy.algorithm.attrs.knn;

/**
 * Interface class that has the following methods:
 * {@link #weight(double)}
 * <p>
 * Created by Whiplash on 19.09.2014.
 */
public interface Weight {

    /**
     * Measures weight
     *
     * @param distance distance between objects
     * @return calculated weight
     */
    public double weight(double distance);

}
