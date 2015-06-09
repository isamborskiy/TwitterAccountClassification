package com.samborskiy.attributes.hashtag;

import com.samborskiy.entities.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class HashTagPerAccount extends HashTagFunction {

    @Override
    public String getName() {
        return "hash_tag_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        attributes.add(new Attribute(tweets.stream()
                .mapToDouble(t -> getHashTags(t).size())
                .average().getAsDouble(), getName()));
    }
}
