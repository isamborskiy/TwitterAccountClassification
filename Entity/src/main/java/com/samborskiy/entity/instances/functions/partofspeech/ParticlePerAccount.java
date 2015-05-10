package com.samborskiy.entity.instances.functions.partofspeech;

import com.samborskiy.entity.instances.Attribute;
import com.samborskiy.entity.instances.functions.AccountFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Number of appearance particle 'не' in the tweets divided by the total number of tweets.
 */
public class ParticlePerAccount extends AccountFunction {

    private static final String NE = "не";

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        double count = 0.;
        for (String tweet : tweets) {
            int index = 0;
            while ((index = tweet.indexOf(NE, index)) != -1) {
                count++;
                index++;
            }
        }
        attributes.add(getAttribute(count / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, String.format("particle_ne_per_tweet", args));
    }
}
