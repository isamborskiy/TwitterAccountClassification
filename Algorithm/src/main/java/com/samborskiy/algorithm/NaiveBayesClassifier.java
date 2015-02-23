package com.samborskiy.algorithm;

import com.samborskiy.entity.Tweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.StrictMath.log;

/**
 * Created by Whiplash on 12.02.2015.
 */
public class NaiveBayesClassifier {

    private static final double ALPHA = 1;

    private List<Tweet> data;
    // class id -> (word -> probability)
    private Map<Integer, Map<String, Double>> probabilities;

    public NaiveBayesClassifier(List<Tweet> data) {
        this.data = data;
    }

    public void train() {
        probabilities = new HashMap<>();
        Map<Integer, List<Tweet>> tweets = splitData();
        Map<String, Double> count = calculateCount(data);
        for (Integer classId : tweets.keySet()) {
            probabilities.put(classId, calculateCount(tweets.get(classId)));
            calculateProbabilityLn(count, probabilities.get(classId));
        }
    }

    private Map<Integer, List<Tweet>> splitData() {
        Map<Integer, List<Tweet>> tweets = new HashMap<>();
        for (Tweet tweet : data) {
            if (tweets.get(tweet.getClassId()) == null) {
                tweets.put(tweet.getClassId(), new ArrayList<>());
            }
            tweets.get(tweet.getClassId()).add(tweet);
        }
        return tweets;
    }

    private Map<String, Double> calculateCount(List<Tweet> tweets) {
        Map<String, Double> probabilities = new HashMap<>();
        for (Tweet tweet : tweets) {
            for (String word : tweet) {
                Double value = probabilities.getOrDefault(word, 0.);
                probabilities.put(word, value + 1);
            }
        }
        return probabilities;
    }

    private void calculateProbabilityLn(Map<String, Double> all, Map<String, Double> part) {
        for (String word : all.keySet()) {
            Double allCountLn = log(all.get(word) + ALPHA);
            Double partCountLn = log(part.getOrDefault(word, 0.) + ALPHA * 2);
            part.put(word, partCountLn - allCountLn);
        }
    }

    public int getClassId(List<Tweet> tweets) {
        if (probabilities == null) {
            throw new IllegalStateException("you need to run method 'train()' first");
        }
        Map<Integer, Integer> classToCount = new HashMap<>();
        for (Tweet tweet : tweets) {
            int classId = getClassId(tweet);
            int count = classToCount.getOrDefault(classId, 0);
            classToCount.put(classId, count + 1);
        }
        return maxElementKey(classToCount);
    }

    public int getClassId(Tweet tweet) {
        Map<Integer, Double> classToProbability = new HashMap<>();
        for (Integer classId : probabilities.keySet()) {
            classToProbability.put(classId, credibilityFunctionLn(tweet, classId));
        }
        return maxElementKey(classToProbability);
    }

    private <K, V extends Comparable<V>> K maxElementKey(Map<K, V> map) {
        V maxValue = null;
        K maxKey = null;
        for (K key : map.keySet()) {
            if (maxValue == null || map.get(key).compareTo(maxValue) == 1) {
                maxKey = key;
                maxValue = map.get(key);
            }
        }
        return maxKey;
    }

    private double credibilityFunctionLn(Tweet tweet, int classId) {
        Map<String, Double> probabilities = this.probabilities.get(classId);
        double probabilityLn = 0;
        for (String word : tweet) {
            probabilityLn += probabilities.getOrDefault(word, 0.);
        }
        return probabilityLn;
    }

}
