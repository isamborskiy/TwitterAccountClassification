package com.samborskiy.algorithm.attrs.knn;

import com.samborskiy.entity.instances.attrs.Account;

/**
 * Interface class that has the following methods:
 * {@link #distance(Instance, Instance)}
 * <p>
 * Created by Whiplash on 15.09.2014.
 */
public interface Distance {

    /**
     * Measures distance between {@code first} and {@code second}
     *
     * @param first  one of required elements
     * @param second another element
     * @return calculated distance
     */
    public double distance(Account first, Account second);

}
