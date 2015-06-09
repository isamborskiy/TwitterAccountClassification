package com.samborskiy.classifier.attributes.length;

import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class AverageAccountLength extends LengthFunction {

    @Override
    public String getName() {
        return "average_tweet_length";
    }

    @Override
    protected List<Attribute> count(List<List<String>> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute(tweets.stream()
                .mapToDouble(List::size)
                .average().getAsDouble(), getName()));
        return attributes;
    }
}
