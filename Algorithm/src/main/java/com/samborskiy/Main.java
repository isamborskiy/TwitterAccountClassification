package com.samborskiy;

import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;
import com.samborskiy.misc.InstancesFromDatabase;

import java.io.File;
import java.util.List;

public class Main {

    public static final String TRAIN_FILE_PATH = "res/ru/config1.json";
    public static final String TEST_FILE_PATH = "res/ru/config2.json";

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration trainConfiguration = Configuration.build(configFileTrain);
        List<Tweet> train = InstancesFromDatabase.getAllTweets(trainConfiguration);

        File configFileTest = new File(TEST_FILE_PATH);
        Configuration testConfiguration = Configuration.build(configFileTest);
        List<List<Tweet>> test = InstancesFromDatabase.getUsersTweets(testConfiguration);

        NaiveBayesClassifier classifier = new NaiveBayesClassifier(train);
        classifier.train();
        int successCounter = 0;
        for (List<Tweet> tweets : test) {
            if (!tweets.isEmpty() && classifier.getClassId(tweets) == tweets.get(0).getClassId()) {
                successCounter++;
            }
        }
        System.out.println("Guess: " + successCounter + " of " + test.size());
    }

}
