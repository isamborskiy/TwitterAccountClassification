package com.samborskiy.algorithm.attrs.knn;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.exp;

/**
 * Kernel function: {@code |x| > 1 ? 0 : exp(-|x|)}
 * <p>
 * Created by Whiplash on 21.09.2014.
 */
public class GaussKernel implements Kernel {

    private static GaussKernel instance;

    /**
     * Returns the {@code GaussKernel} object associated with the current Java application.
     * Most of the methods of class {@code DistanceWeight} are instance
     * methods and must be invoked with respect to the current runtime object.
     *
     * @return {@code GaussKernel} object associated with the current
     * Java application.
     */
    public static GaussKernel getInstance() {
        if (instance == null) {
            instance = new GaussKernel();
        }
        return instance;
    }

    private GaussKernel() {
    }

    @Override
    public double eval(double value) {
        return abs(value) > 1 ? 0 : exp(-abs(value));
    }

}
