package com.samborskiy.entity.instances.functions.length;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class AverageTweetLength extends LengthFunction {

    @Override
    protected List<Attribute> count(List<List<String>> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(getAttribute(tweets.stream()
                .mapToDouble(List::size)
                .average().getAsDouble()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "average_tweet_length");
    }
}
