package com.samborskiy;

import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;
import com.samborskiy.misc.InstancesFromDatabase;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static final String TRAIN_FILE_PATH = "res/ru/config_1.json";
    public static final String TEST_FILE_PATH = "res/ru/config_2.json";

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration trainConfiguration = Configuration.build(configFileTrain);
        List<Tweet> train = InstancesFromDatabase.getAllTweets(trainConfiguration);

        File configFileTest = new File(TEST_FILE_PATH);
        Configuration testConfiguration = Configuration.build(configFileTest);
        List<List<Tweet>> test = InstancesFromDatabase.getUsersTweets(testConfiguration);

        NaiveBayesClassifier classifier = new NaiveBayesClassifier(train);
        classifier.train();
        Map<Integer, Integer> successCounter = new HashMap<>();
        Map<Integer, Integer> counter = new HashMap<>();
        test.stream().filter(tweets -> !tweets.isEmpty()).forEach(tweets -> {
            int classId = tweets.get(0).getClassId();
            if (classifier.getClassId(tweets) == classId) {
                successCounter.put(classId, successCounter.getOrDefault(classId, 0) + 1);
            }
            counter.put(classId, counter.getOrDefault(classId, 0) + 1);
        });

        for (Integer classId : counter.keySet()) {
            System.out.format("Guess: %d of %d for classId = %d\n", successCounter.getOrDefault(classId, 0), counter.get(classId), classId);
        }
    }

}
