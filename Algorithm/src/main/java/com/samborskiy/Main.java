package com.samborskiy;

import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;
import com.samborskiy.misc.InstancesFromDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int K = 5;
    private static final int N = 50;

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration trainConfiguration = Configuration.build(configFileTrain);
        List<List<Tweet>> sample = InstancesFromDatabase.getAllAccounts(trainConfiguration);

        List<Double> counter = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            Collections.shuffle(sample);
            List<List<Tweet>> userTrain = sample.subList(0, sample.size() * (K - 1) / K);
            List<List<Tweet>> test = sample.subList(sample.size() * (K - 1) / K, sample.size());
            List<Tweet> train = new ArrayList<>();
            for (List<Tweet> userTweets : userTrain) {
                train.addAll(userTweets.stream().collect(Collectors.toList()));
            }
            NaiveBayesClassifier classifier = new NaiveBayesClassifier(train);
            classifier.train();
            double success = 0;
            for (List<Tweet> tweets : test) {
                if (!tweets.isEmpty()) {
                    if (classifier.getClassId(tweets) == tweets.get(0).getClassId()) {
                        success++;
                    }
                }
            }
            counter.add(success / test.size());
        }

        double avrSuccess = 0.;
        for (Double success : counter) {
            avrSuccess += success;
        }
        avrSuccess = avrSuccess / N;
        System.out.format("Avr success: %f", avrSuccess);
    }

}
