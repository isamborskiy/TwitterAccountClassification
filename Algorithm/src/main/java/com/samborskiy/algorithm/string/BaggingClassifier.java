package com.samborskiy.algorithm.string;

import com.samborskiy.entity.Language;
import com.samborskiy.entity.instances.string.Instance;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whiplash on 13.04.2015.
 */
public class BaggingClassifier<E extends Instance> extends Classifier<E> {

    private final List<Classifier<E>> classifiers;

    public BaggingClassifier(Language language, InputStream is, List<Classifier<E>> classifiers) {
        super(language, is);
        this.classifiers = classifiers;
    }

    public BaggingClassifier(Language language, List<Classifier<E>> classifiers) {
        super(language);
        this.classifiers = classifiers;
    }

    @Override
    public void train(List<List<E>> data) {
        for (int i = 0; i < classifiers.size(); i++) {
            Classifier<E> classifier = classifiers.get(i);
            Collections.shuffle(data.get(i));
            List<E> trainData = data.get(i).subList(0, data.get(i).size() / 2);
            classifier.train(wrap(trainData));
        }
    }

    private <T> List<T> wrap(T list) {
        List<T> wrapper = new ArrayList<>();
        wrapper.add(list);
        return wrapper;
    }

    @Override
    public void clear() {
        classifiers.forEach(Classifier::clear);
    }

    @Override
    public int getClassId(List<Instance> element) {
        Map<Integer, Integer> poll = new HashMap<>();
        for (int i = 0; i < classifiers.size(); i++) {
            Classifier<E> classifier = classifiers.get(i);
            int classId = classifier.getClassId(wrap(element.get(i)));
            int weight = poll.getOrDefault(classId, 0) + 1;
            poll.put(classId, weight + 1);
        }
        return maxElementKey(poll);
    }

    @Override
    protected void read(InputStream in) {
        throw new UnsupportedOperationException("Method not realised");
    }

    @Override
    public void write(OutputStream out) {
        throw new UnsupportedOperationException("Method not realised");
    }
}
