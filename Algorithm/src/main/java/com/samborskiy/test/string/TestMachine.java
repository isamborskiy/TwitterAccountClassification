package com.samborskiy.test.string;

import com.samborskiy.algorithm.string.Classifier;
import com.samborskiy.entity.instances.string.Instance;
import com.samborskiy.test.Statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 30.03.2015.
 */
public class TestMachine<E extends Instance> {

    private final Classifier<E> classifier;
    private final List<List<E>> data;
    private final int size;
    private final int[][] testConfusionMatrix;

    public TestMachine(Classifier<E> classifier, int classNumber, List<List<E>> data) {
        this.classifier = classifier;
        this.data = new ArrayList<>(data);
        this.size = data.get(0).size();
        this.testConfusionMatrix = new int[classNumber][classNumber];
    }

    public Statistics crossValidationTest(int foldCount, int rounds, boolean parallelTest) {
        int foldSize = size / foldCount;
        for (int k = 0; k < rounds; k++) {
            Collections.shuffle(data);
            List<List<E>> trainingData = new ArrayList<>();
            List<List<E>> testData = new ArrayList<>();
            for (int i = 0; i < foldCount; i++) {
                trainingData.clear();
                classifier.clear();
                for (List<E> instances : data) {
                    int l = i * foldSize;
                    int r = l + foldSize;
                    testData.add(instances.subList(l, r));
                    ArrayList<E> list = new ArrayList<>();
                    list.addAll(instances.subList(0, l));
                    list.addAll(instances.subList(r, size));
                    trainingData.add(list);
                }
                classifier.train(trainingData);
                testInternal(testData, parallelTest);
            }
        }
        return getCurrentStatistic();
    }

//    public Statistics crossValidationTest(int foldCount, int rounds, boolean parallelTest) {
//        int foldSize = size / foldCount;
//        for (int k = 0; k < rounds; k++) {
//            Collections.shuffle(data);
//            List<E> trainingData = new ArrayList<>(size - foldSize);
//            List<E> testData;
//            for (int i = 0; i < foldCount; i++) {
//                trainingData.clear();
//                int l = i * foldSize;
//                int r = l + foldSize;
//                testData = data.subList(l, r);
//                trainingData.addAll(data.subList(0, l));
//                trainingData.addAll(data.subList(r, size));
//                classifier.clear();
//                classifier.train(trainingData);
//                testInternal(testData, parallelTest);
//            }
//        }
//        return getCurrentStatistic();
//    }

    private Statistics testInternal(List<List<E>> testData, boolean parallelTest) {
        clearConfusionMatrix();
        if (!parallelTest) {
            testInternal(classifier, testData, testConfusionMatrix);
        } else {
            parallelTestInternal(classifier, testData, testConfusionMatrix);
        }
        return getCurrentStatistic();
    }

    public Statistics getCurrentStatistic() {
        return Statistics.createStatistics(testConfusionMatrix);
    }

    private void testInternal(Classifier<E> classifier, List<List<E>> data, int[][] confusionMatrix) {
        for (int i = 0; i < data.size(); i++) {
            List<Instance> instances = getInstances(data, i);
            int classId = instances.get(0).getClassId();
            confusionMatrix[classifier.getClassId(instances)][classId]++;
        }
    }

    private void parallelTestInternal(Classifier<E> classifier, List<List<E>> data, int[][] confusionMatrix) {
//        ExecutorService executor = generateExecutorService();
//        List<Future<Integer>> futureList = new ArrayList<>(data.size());
//        for (E elem : data) {
//            futureList.add(executor.submit(() -> classifier.getClassId(elem)));
//        }
//        try {
//            executor.shutdown();
//            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
//            for (int i = 0; i < data.size(); i++) {
//                confusionMatrix[futureList.get(i).get()][data.get(i).getClassId()]++;
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    private List<Instance> getInstances(List<List<E>> data, int index) {
        return data.stream().map(list -> list.get(index)).collect(Collectors.toList());
    }

    private ExecutorService generateExecutorService() {
        return Executors.newFixedThreadPool(3);
    }

    private void clearConfusionMatrix() {
        for (int[] arr : testConfusionMatrix) {
            Arrays.fill(arr, 0);
        }
    }
}
