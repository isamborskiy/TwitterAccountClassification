package com.samborskiy.algorithm;

import com.samborskiy.entity.Language;
import com.samborskiy.entity.instances.string.Instance;

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
    public void train(List<List<E>> data) {
        probabilities = new HashMap<>();
        Map<Integer, List<E>> classIdToInstance = splitData(data.get(0));
        Map<String, Double> count = calculateCount(data.get(0));
        for (Integer classId : classIdToInstance.keySet()) {
            probabilities.put(classId, calculateCount(classIdToInstance.get(classId)));
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
    public int getClassId(List<Instance> instance) {
        Map<Integer, Double> classToProbability = new HashMap<>();
        for (Integer classId : probabilities.keySet()) {
            classToProbability.put(classId, credibilityFunctionLn(instance.get(0), classId));
        }
        return maxElementKey(classToProbability);
    }

    protected double credibilityFunctionLn(Instance instance, int classId) {
        Map<String, Double> probabilities = this.probabilities.get(classId);
        double probabilityLn = 0;
        for (String word : instance) {
            probabilityLn += probabilities.getOrDefault(word, 0.);
        }
        return probabilityLn;
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
}
