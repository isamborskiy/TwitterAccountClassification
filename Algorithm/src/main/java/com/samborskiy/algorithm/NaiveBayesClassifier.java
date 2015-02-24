package com.samborskiy.algorithm;

import com.samborskiy.entity.Tweet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.StrictMath.log;

/**
 * Created by Whiplash on 12.02.2015.
 */
public class NaiveBayesClassifier extends Classifier {

    private static final double ALPHA = 1;

    // class id -> (word -> probability)
    private Map<Integer, Map<String, Double>> probabilities;

    public NaiveBayesClassifier(InputStream in) {
        super(in);
    }

    public NaiveBayesClassifier(List<Tweet> data) {
        super(data);
    }

    @Override
    public void train() {
        probabilities = new HashMap<>();
        Map<Integer, List<Tweet>> tweets = splitData();
        Map<String, Double> count = calculateCount(getData());
        for (Integer classId : tweets.keySet()) {
            probabilities.put(classId, calculateCount(tweets.get(classId)));
            calculateProbabilityLn(count, probabilities.get(classId));
        }
    }

    private Map<Integer, List<Tweet>> splitData() {
        Map<Integer, List<Tweet>> tweets = new HashMap<>();
        for (Tweet tweet : getData()) {
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

    @Override
    public int getClassId(Tweet tweet) {
        Map<Integer, Double> classToProbability = new HashMap<>();
        for (Integer classId : probabilities.keySet()) {
            classToProbability.put(classId, credibilityFunctionLn(tweet, classId));
        }
        return maxElementKey(classToProbability);
    }

    @Override
    public void read(InputStream in) {
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(in))) {
            probabilities = new HashMap<>();
            Map<String, Double> classIdProbabilities = null;
            String line;
            while ((line = bf.readLine()) != null) {
                if (line.startsWith("Class id ")) {
                    classIdProbabilities = new HashMap<>();
                    probabilities.put(Integer.parseInt(line.replace("Class id ", "")), classIdProbabilities);
                } else {
                    String[] parseLine = line.split(" ");
                    classIdProbabilities.put(parseLine[0], Double.parseDouble(parseLine[1]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(OutputStream out) {
        try (PrintWriter pw = new PrintWriter(out)) {
            for (Integer classId : probabilities.keySet()) {
                pw.format("Class id %d\n", classId);
                for (String word : probabilities.get(classId).keySet()) {
                    pw.format(Locale.US, "%s %f\n", word, probabilities.get(classId).get(word));
                }
            }
        }
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
