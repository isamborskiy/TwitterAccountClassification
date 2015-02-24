package com.samborskiy.algorithm;

import com.samborskiy.entity.Tweet;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Whiplash on 24.02.2015.
 */
public abstract class Classifier {

    private List<Tweet> data;

    public Classifier(InputStream in) {
        // use method read to initialize classifier
        read(in);
    }

    public Classifier(List<Tweet> data) {
        this.data = data;
    }

    public List<Tweet> getData() {
        return data;
    }

    public abstract void train();

    public int getClassId(List<Tweet> tweets) {
        Map<Integer, Integer> classToCount = new HashMap<>();
        for (Tweet tweet : tweets) {
            int classId = getClassId(tweet);
            int count = classToCount.getOrDefault(classId, 0);
            classToCount.put(classId, count + 1);
        }
        return maxElementKey(classToCount);
    }

    public abstract int getClassId(Tweet tweet);

    protected <K, V extends Comparable<V>> K maxElementKey(Map<K, V> map) {
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

    public abstract void read(InputStream in);

    public abstract void write(OutputStream out);

}
