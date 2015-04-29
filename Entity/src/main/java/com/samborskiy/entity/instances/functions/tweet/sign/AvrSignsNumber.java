package com.samborskiy.entity.instances.functions.tweet.sign;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * The average number of different signs in a tweets.
 */
public class AvrSignsNumber extends SignFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attrs = new ArrayList<>();
        double count = 0.;
        for (String tweet : tweets) {
            for (String sign : SIGNS.keySet()) {
                if (tweet.contains(sign)) {
                    count++;
                }
            }
        }
        attrs.add(getAttribute(count / tweets.size()));
        return attrs;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "average_signs_number");
    }
}