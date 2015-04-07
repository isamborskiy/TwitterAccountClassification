package com.samborskiy.algorithm;

import com.samborskiy.entity.Instance;
import com.samborskiy.entity.Language;

import java.io.InputStream;

public class NaiveBayesClassifierStemmer<E extends Instance> extends NaiveBayesClassifier<E> {

    public NaiveBayesClassifierStemmer(Language language, InputStream in) {
        super(language, in);
    }

    public NaiveBayesClassifierStemmer(Language language) {
        super(language);
    }

    @Override
    protected String modifyWord(String word) {
        return language.stem(word);
    }
}
