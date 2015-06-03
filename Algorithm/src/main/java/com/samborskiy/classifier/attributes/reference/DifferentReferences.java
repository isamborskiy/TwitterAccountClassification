package com.samborskiy.classifier.attributes.reference;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class DifferentReferences extends ReferenceFunction {

    public DifferentReferences(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                               MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        Set<String> references = new HashSet<>();
        for (String tweet : tweets) {
            references.addAll(getReferences(tweet.toLowerCase()).stream().collect(Collectors.toList()));
        }
        attributes.add(getAttribute(((double) references.size()) / tweets.size()));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "different_references_per_tweet");
    }
}
