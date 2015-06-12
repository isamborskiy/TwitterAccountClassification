package com.samborskiy.attributes.reference;

import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class ReferencePerAccount extends ReferenceFunction {

    @Override
    public String getName() {
        return "reference_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        attributes.add(new Attribute(tweets.stream()
                .mapToDouble(t -> getReferences(t).size())
                .average().getAsDouble(), getName()));
    }
}
