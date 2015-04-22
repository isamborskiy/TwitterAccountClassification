package com.samborskiy.algorithm.attrs.knn;

import com.samborskiy.algorithm.attrs.Classifier;
import com.samborskiy.entity.instances.attrs.Account;

import java.util.Arrays;
import java.util.List;

/**
 * Realization of KNN algorithm
 * <p>
 * Created by Whiplash on 19.09.2014.
 *
 * @see <a href="http://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm">KNN</a>
 */
public class KNN extends Classifier {

    private Distance distanceFunction;
    private Weight weightFunction;
    private int k;

    public KNN(List<Account> data, Distance distanceFunction, Weight weightFunction, int k) {
        super(data);
        this.distanceFunction = distanceFunction;
        this.weightFunction = weightFunction;
        this.k = Math.min(k, size);
    }

    @Override
    public void train() {
    }

    @Override
    public int getClassId(Account account) {
        Integer[] ids = new Integer[size];
        double[] distances = new double[size];
        Arrays.setAll(ids, i -> i);
        Arrays.setAll(distances, i -> distanceFunction.distance(account, get(i)));
        Arrays.sort(ids, (Integer o1, Integer o2) -> Double.compare(distances[o1], distances[o2]));

        double[] weights = new double[getClassNumber()];
        for (int i = 0; i < k; i++) {
            Account neighbor = get(ids[i]);
            weights[neighbor.getClassId()] += weightFunction.weight(distances[ids[i]]);
        }

        return indexOfMaxElement(weights);
    }

    private int indexOfMaxElement(double[] array) {
        double maxValue = 0;
        int maxId = -1;
        for (int i = 0; i < array.length; i++) {
            if (maxValue < array[i]) {
                maxValue = array[i];
                maxId = i;
            }
        }
        return maxId;
    }
}
