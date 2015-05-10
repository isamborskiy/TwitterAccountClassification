package com.samborskiy.entity.functions.reference;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class DifferentReferences extends ReferenceFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        Set<String> references = new HashSet<>();
        for (String tweet : tweets) {
            references.addAll(getReferences(tweet.toLowerCase()).stream().collect(Collectors.toList()));
        }
        attributes.add(getAttribute(((double) references.size()) / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "different_references_per_tweet");
    }
}
