package com.samborskiy.classifier.attributes.reference;

import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class DifferentReferences extends ReferenceFunction {

    @Override
    public String getName() {
        return "different_references_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        Set<String> references = new HashSet<>();
        for (String tweet : tweets) {
            references.addAll(getReferences(tweet.toLowerCase()).stream().collect(Collectors.toList()));
        }
        attributes.add(new Attribute(((double) references.size()) / tweets.size(), getName()));
    }
}
