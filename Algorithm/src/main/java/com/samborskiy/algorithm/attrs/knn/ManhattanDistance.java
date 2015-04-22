package com.samborskiy.algorithm.attrs.knn;

import com.samborskiy.entity.instances.attrs.Account;

/**
 * Created by warrior on 19.09.14.
 */
public class ManhattanDistance implements Distance {

    @Override
    public double distance(Account first, Account second) {
        double result = 0;
        for (int i = 0; i < first.size(); i++) {
            result += StrictMath.abs(first.get(i) - second.get(i));
        }
        return result;
    }
}
