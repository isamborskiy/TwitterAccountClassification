package com.samborskiy.entity.instances.functions.tweet.hashtag;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class HashTagLength extends HashTagFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        double length = 0.;
        for (String tweet : tweets) {
            for (String hashTag : getHashTags(tweet)) {
                length += hashTag.length();
            }
        }
        attributes.add(getAttribute(length / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "hash_tag_length");
    }
}
