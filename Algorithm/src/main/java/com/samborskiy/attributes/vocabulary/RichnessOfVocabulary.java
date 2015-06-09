package com.samborskiy.attributes.vocabulary;

import com.samborskiy.attributes.AttributeFunction;
import com.samborskiy.entities.Attribute;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class RichnessOfVocabulary extends AttributeFunction {

    @Override
    public String getName() {
        return "richness_of_vocabulary";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        Set<String> words = new HashSet<>();
        for (String tweet : tweets) {
            words.addAll(tweetParser.parse(tweet));
        }
        attributes.add(new Attribute(((double) words.size()) / tweets.size(), getName()));
    }
}
