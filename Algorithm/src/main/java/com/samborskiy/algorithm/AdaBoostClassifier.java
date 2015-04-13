package com.samborskiy.algorithm;

import com.samborskiy.entity.Language;
import com.samborskiy.entity.instances.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by whiplash on 13.04.2015.
 */
public class AdaBoostClassifier<E extends Instance> extends Classifier<E> {

    private final List<Classifier<E>> classifiers;
    private final List<Double> weights;
    private final List<TweetModifier> modifiers;

    public AdaBoostClassifier(Language language, InputStream is, List<Classifier<E>> classifiers, List<Double> weights, List<TweetModifier> modifiers) {
        super(language, is);
        this.classifiers = classifiers;
        this.weights = weights;
        this.modifiers = modifiers;
    }

    public AdaBoostClassifier(Language language, List<Classifier<E>> classifiers, List<Double> weights, List<TweetModifier> modifiers) {
        super(language);
        this.classifiers = classifiers;
        this.weights = weights;
        this.modifiers = modifiers;
    }

    @Override
    public void train(List<E> data) {
        for (int i = 0; i < classifiers.size(); i++) {
            Classifier<E> classifier = classifiers.get(i);
            TweetModifier modifier = modifiers.get(i);
            List<E> modifiedData = data.stream().map(instance -> modify(instance, modifier)).collect(Collectors.toList());
            classifier.train(modifiedData);
        }
    }

    private E modify(Instance instance, TweetModifier modifier) {
        String text = "";
        for (String str : instance) {
            text += " " + str;
        }
        if (instance instanceof Tweet) {
            return (E) new Tweet(text, instance.getClassId(), language, modifier);
        } else if (instance instanceof Account) {
            Account account = new Account(instance.getClassId());
            account.addWords(modifier.apply(text, language));
            return (E) account;
        } else if (instance instanceof AccountWithTweet) {
            throw new UnsupportedOperationException("Method not realised");
        }
        return null;
    }

    @Override
    public void clear() {
        for (Classifier classifier : classifiers) {
            classifier.clear();
        }
    }

    @Override
    public int getClassId(Instance element) {
        Map<Integer, Double> poll = new HashMap<>();
        for (int i = 0; i < classifiers.size(); i++) {
            Classifier<E> classifier = classifiers.get(i);
            TweetModifier modifier = modifiers.get(i);
            E instance = modify(element, modifier);
            int classId = classifier.getClassId(instance);
            double weight = poll.getOrDefault(classId, 0.);
            poll.put(classId, weight + weights.get(i));
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
