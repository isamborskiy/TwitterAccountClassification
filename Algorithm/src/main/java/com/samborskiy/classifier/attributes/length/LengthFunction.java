package com.samborskiy.classifier.attributes.length;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 27.04.2015.
 */
public abstract class LengthFunction extends AttributeFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        return count(tweets.stream().map(tweetParser::parse).collect(Collectors.toList()));
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        // empty...
    }

    protected abstract List<Attribute> count(List<List<String>> tweets);
}
