package com.samborskiy.entity.instances.functions.reference;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class ReferencePerAttribute extends ReferenceFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(getAttribute(tweets.stream()
                .mapToDouble(t -> getReferences(t).size())
                .average().getAsDouble()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "reference_per_tweet");
    }
}
