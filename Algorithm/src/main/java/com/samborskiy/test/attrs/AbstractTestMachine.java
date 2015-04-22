package com.samborskiy.test.attrs;

import com.samborskiy.algorithm.attrs.Classifier;
import com.samborskiy.entity.instances.attrs.Account;
import com.samborskiy.test.Statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by warrior on 19.09.14.
 */
public abstract class AbstractTestMachine {

    protected List<Account> dataSet;

    private boolean parallelTest;

    private int size;
    private int[][] testConfusionMatrix;

    public AbstractTestMachine(List<Account> dataSet) {
        this(dataSet, false);
    }

    public AbstractTestMachine(List<Account> dataSet, boolean parallelTest) {
        checkNotEmptyData(dataSet);
        this.dataSet = new ArrayList<>(dataSet);
        this.parallelTest = parallelTest;
        size = dataSet.size();
        int classNumber = dataSet.get(0).getClassId();
        testConfusionMatrix = new int[classNumber][classNumber];
    }

    public Statistics crossValidationTest(int foldCount, int rounds) {
        clearConfusionMatrix();
        final int foldSize = size / foldCount;
        for (int k = 0; k < rounds; k++) {
            Collections.shuffle(dataSet);
            List<Account> trainingData = new ArrayList<>(size - foldSize);
            List<Account> testData;
            for (int i = 0; i < foldCount; i++) {
                trainingData.clear();
                int l = i * foldSize;
                int r = l + foldSize;
                testData = dataSet.subList(l, r);
                trainingData.addAll(dataSet.subList(0, l));
                trainingData.addAll(dataSet.subList(r, size));
                testInternal(trainingData, testData);
            }
        }
        return getCurrentStatistic();
    }

    public Statistics randomSubSamplingTest(int foldSize, int rounds) {
        clearConfusionMatrix();
        for (int i = 0; i < rounds; i++) {
            Collections.shuffle(dataSet);
            List<Account> testData = dataSet.subList(0, foldSize);
            List<Account> trainingData = dataSet.subList(foldSize, size);
            testInternal(trainingData, testData);
        }

        return getCurrentStatistic();
    }

    public Statistics leaveOneOut() {
        clearConfusionMatrix();
        List<Account> trainingData = new ArrayList<>(size - 1);
        for (int i = 0; i < size; i++) {
            trainingData.clear();
            List<Account> testData = Arrays.asList(dataSet.get(i));
            trainingData.addAll(dataSet.subList(0, i));
            trainingData.addAll(dataSet.subList(i + 1, size));
            testInternal(trainingData, testData);
        }
        return getCurrentStatistic();
    }

    public Statistics test(List<Account> testData) {
        return testInternal(dataSet, testData);
    }

    protected Statistics testInternal(List<Account> trainingData, List<Account> testData) {
        clearConfusionMatrix();
        Classifier classifier = createClassifier(trainingData);
        classifier.train();
        if (parallelTest) {
            testInternal(classifier, testData, testConfusionMatrix);
        } else {
            parallelTestInternal(classifier, testData, testConfusionMatrix);
        }
        return getCurrentStatistic();
    }

    protected void testInternal(Classifier classifier, List<Account> dataSet, int[][] confusionMatrix) {
        for (Account account : dataSet) {
            confusionMatrix[classifier.getClassId(account)][account.getClassId()]++;
        }
    }

    protected void parallelTestInternal(Classifier classifier, List<Account> dataSet, int[][] confusionMatrix) {
        ExecutorService executor = generateExecutorService();
        List<Future<Integer>> futureList = new ArrayList<>(dataSet.size());
        for (Account account : dataSet) {
            futureList.add(executor.submit(() -> classifier.getClassId(account)));
        }
        try {
            executor.shutdown();
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
            for (int i = 0; i < dataSet.size(); i++) {
                confusionMatrix[futureList.get(i).get()][dataSet.get(i).getClassId()]++;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    protected ExecutorService generateExecutorService() {
        return Executors.newFixedThreadPool(3);
    }

    public void clearConfusionMatrix() {
        for (int[] arr : testConfusionMatrix) {
            Arrays.fill(arr, 0);
        }
    }

    public Statistics getCurrentStatistic() {
        return Statistics.createStatistics(testConfusionMatrix);
    }

    private static void checkNotEmptyData(List<?> dataSet) {
        if (dataSet == null || dataSet.isEmpty()) {
            throw new IllegalArgumentException("DataSet must be not empty");
        }
    }

    public boolean isParallelTest() {
        return parallelTest;
    }

    public void setParallelTest(boolean parallelTest) {
        this.parallelTest = parallelTest;
    }

    protected abstract Classifier createClassifier(List<Account> dataSet);

}
