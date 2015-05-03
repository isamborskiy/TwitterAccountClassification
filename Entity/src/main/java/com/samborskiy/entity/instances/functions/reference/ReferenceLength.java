package com.samborskiy.entity.instances.functions.reference;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class ReferenceLength extends ReferenceFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        double length = 0.;
        for (String tweet : tweets) {
            for (String reference : getReferences(tweet)) {
                length += reference.length();
            }
        }
        attributes.add(getAttribute(length / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "reference_length");
    }
}
