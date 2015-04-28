package com.samborskiy.entity.instances.functions.hashtag;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class HashTagPerTweet extends HashTagFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(getAttribute(tweets.stream().mapToDouble(t -> getHashTags(t).size()).average().getAsDouble()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "hash_tag_per_tweet");
    }
}
