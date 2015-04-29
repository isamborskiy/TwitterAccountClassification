package com.samborskiy.entity.instances.functions.tweet.hashtag;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class DifferentHashTags extends HashTagFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        Set<String> hashTags = new HashSet<>();
        for (String tweet : tweets) {
            hashTags.addAll(getHashTags(tweet.toLowerCase()).stream().collect(Collectors.toList()));
        }
        attributes.add(getAttribute(((double) hashTags.size()) / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "different_hash_tags_per_tweet");
    }
}
