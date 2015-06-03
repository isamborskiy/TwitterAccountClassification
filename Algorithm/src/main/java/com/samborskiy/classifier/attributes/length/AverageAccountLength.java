package com.samborskiy.classifier.attributes.length;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class AverageAccountLength extends LengthFunction {

    public AverageAccountLength(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                                MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected List<Attribute> count(List<List<String>> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(getAttribute(tweets.stream()
                .mapToDouble(List::size)
                .average().getAsDouble()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "average_tweet_length");
    }
}
