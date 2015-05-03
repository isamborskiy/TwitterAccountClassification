package com.samborskiy.entity.instances.functions.vocabulary;

import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.instances.Attribute;
import com.samborskiy.entity.instances.functions.AttributeFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class UnknownWordsPerAttribute extends AttributeFunction {

    private final MorphologicalAnalyzer morphologicalAnalyzer = MorphologicalAnalyzer.get();

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        TweetParser parser = TweetParser.get();
        double count = 0.;
        for (String tweet : tweets) {
            for (String word : parser.parse(tweet)) {
                if (morphologicalAnalyzer.get(word) == null) {
                    count++;
                }
            }
        }
        attributes.add(getAttribute(count / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "unknown_word_per_tweet");
    }
}
