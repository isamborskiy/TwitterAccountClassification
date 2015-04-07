package com.samborskiy.algorithm;

import com.samborskiy.entity.Instance;
import com.samborskiy.entity.Language;

import java.io.InputStream;

public class NaiveBayesClassifierSimple<E extends Instance> extends NaiveBayesClassifier<E> {

    public NaiveBayesClassifierSimple(Language language, InputStream in) {
        super(language, in);
    }

    public NaiveBayesClassifierSimple(Language language) {
        super(language);
    }

    @Override
    protected String modifyWord(String word) {
        return word;
    }
}
