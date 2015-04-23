package com.samborskiy.test.attrs.testmachines;

import com.samborskiy.algorithm.attrs.Classifier;
import com.samborskiy.entity.instances.attrs.Account;
import com.samborskiy.test.attrs.testmachines.AbstractTestMachine;

import java.util.List;

/**
 * Created by warrior on 20.10.14.
 */
public abstract class TestMachine extends AbstractTestMachine {

    public TestMachine(List<Account> dataSet) {
        super(dataSet);
    }

    public TestMachine(List<Account> dataSet, boolean parallelTest) {
        super(dataSet, parallelTest);
    }

    @Override
    protected abstract Classifier createClassifier(List<Account> dataSet);
}
