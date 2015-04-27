package com.samborskiy.entity.instances.functions.vocabulary;

import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.instances.Attribute;
import com.samborskiy.entity.instances.functions.AttributeFunction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class RichnessOfVocabulary extends AttributeFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        Set<String> words = new HashSet<>();
        TweetParser parser = TweetParser.get();
        for (String tweet : tweets) {
            words.addAll(parser.parse(tweet));
        }
        attributes.add(getAttribute(((double) words.size()) / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "richness_of_vocabulary");
    }
}
