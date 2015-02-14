// CHECKSTYLE:OFF
package com.samborskiy;

import com.samborskiy.algorithm.NaiveBayesClassifier;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;
import com.samborskiy.entity.utils.EntityUtil;
import com.samborskiy.extraction.utils.TwitterHelper;
import com.samborskiy.misc.InstancesFromDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("res/ru/config.json");
        Configuration configuration = Configuration.build(file);
        List<Tweet> sample = InstancesFromDatabase.get(configuration);
        NaiveBayesClassifier classifier = new NaiveBayesClassifier(sample);
        classifier.train();
        TwitterHelper twitterHelper = new TwitterHelper(configuration);
        String[] tweetsArray = EntityUtil.deserialize(twitterHelper.getTweets("yandex", configuration.getNumberOfPersonalAccounts()).getBytes(), String[].class);
        List<Tweet> tweets = new ArrayList<>();
        for (String tweet : tweetsArray) {
            tweets.add(new Tweet(tweet, configuration));
        }
        System.out.println(classifier.getClassId(tweets));
    }

}
