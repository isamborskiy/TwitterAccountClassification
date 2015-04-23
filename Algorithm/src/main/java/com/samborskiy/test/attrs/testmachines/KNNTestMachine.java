package com.samborskiy.test.attrs.testmachines;

import com.samborskiy.algorithm.attrs.Classifier;
import com.samborskiy.algorithm.attrs.knn.DistanceWeight;
import com.samborskiy.algorithm.attrs.knn.KNN;
import com.samborskiy.algorithm.attrs.knn.ManhattanDistance;
import com.samborskiy.entity.instances.attrs.Account;

import java.util.List;

/**
 * Created by Whiplash on 19.09.2014.
 */
public class KNNTestMachine extends TestMachine {

    private int k = 1;

    public KNNTestMachine(List<Account> dataSet) {
        super(dataSet);
    }

    public KNNTestMachine(List<Account> dataSet, boolean parallelTest) {
        super(dataSet, parallelTest);
    }

    public void setK(int k) {
        this.k = k;
    }

    @Override
    protected Classifier createClassifier(List<Account> dataSet) {
        return new KNN(dataSet, new ManhattanDistance(), new DistanceWeight(), k);
    }
}
