package com.samborskiy.algorithm;

import com.samborskiy.entity.Language;
import com.samborskiy.entity.instances.AccountWithTweet;
import com.samborskiy.entity.instances.Instance;
import com.samborskiy.entity.instances.Tweet;

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
public class NaiveBayesClassifier<E extends Instance> extends Classifier<E> {

    private static final double ALPHA = 1;

    // class id -> (word -> probability)
    protected Map<Integer, Map<String, Double>> probabilities;

    public NaiveBayesClassifier(Language language, InputStream in) {
        super(language, in);
    }

    public NaiveBayesClassifier(Language language) {
        super(language);
    }

    @Override
    public void train(List<E> data) {
        probabilities = new HashMap<>();
        Map<Integer, List<E>> tweets = splitData(data);
        Map<String, Double> count = calculateCount(data);
        for (Integer classId : tweets.keySet()) {
            probabilities.put(classId, calculateCount(tweets.get(classId)));
            calculateProbabilityLn(count, probabilities.get(classId));
        }
    }

    @Override
    public void clear() {
        probabilities = null;
    }

    protected Map<Integer, List<E>> splitData(List<E> data) {
        Map<Integer, List<E>> instances = new HashMap<>();
        for (E instance : data) {
            if (instances.get(instance.getClassId()) == null) {
                instances.put(instance.getClassId(), new ArrayList<>());
            }
            instances.get(instance.getClassId()).add(instance);
        }
        return instances;
    }

    protected Map<String, Double> calculateCount(List<E> data) {
        Map<String, Double> probabilities = new HashMap<>();
        for (E instance : data) {
            for (String word : instance) {
                Double value = probabilities.getOrDefault(word, 0.);
                probabilities.put(word, value + 1);
            }
        }
        return probabilities;
    }

    protected void calculateProbabilityLn(Map<String, Double> all, Map<String, Double> part) {
        for (String word : all.keySet()) {
            Double allCountLn = log(all.get(word) + ALPHA);
            Double partCountLn = log(part.getOrDefault(word, 0.) + ALPHA * 2);
            part.put(word, partCountLn - allCountLn);
        }
    }

    @Override
    public int getClassId(Instance instance) {
        if (instance instanceof AccountWithTweet) {
            return getClassIdByTweet((AccountWithTweet) instance);
        } else {
            Map<Integer, Double> classToProbability = new HashMap<>();
            for (Integer classId : probabilities.keySet()) {
                classToProbability.put(classId, credibilityFunctionLn(instance, classId));
            }
            return maxElementKey(classToProbability);
        }
    }

    public int getClassIdByTweet(AccountWithTweet account) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < account.tweetNumber(); i++) {
            Tweet tweet = account.getTweet(i);
            int classId = getClassId(tweet);
            int c = count.getOrDefault(classId, 0);
            count.put(classId, c + 1);
        }
        if (count.isEmpty()) {
            return 0;
        } else {
            return maxElementKey(count);
        }
    }

    @Override
    protected void read(InputStream in) {
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

    protected double credibilityFunctionLn(Instance instance, int classId) {
        Map<String, Double> probabilities = this.probabilities.get(classId);
        double probabilityLn = 0;
        for (String word : instance) {
            probabilityLn += probabilities.getOrDefault(word, 0.);
        }
        return probabilityLn;
    }

}
