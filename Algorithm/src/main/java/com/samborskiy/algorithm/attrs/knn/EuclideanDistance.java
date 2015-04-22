package com.samborskiy.algorithm.attrs.knn;

import com.samborskiy.entity.instances.attrs.Account;

/**
 * Created by warrior on 19.09.14.
 */
public class EuclideanDistance implements Distance {

    @Override
    public double distance(Account first, Account second) {
        double result = 0;
        for (int i = 0; i < first.size(); i++) {
            result += StrictMath.pow(first.get(i) - second.get(i), 2);
        }
        return StrictMath.sqrt(result);
    }
}
