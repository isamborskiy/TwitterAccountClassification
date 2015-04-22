package com.samborskiy.entity.instances.attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * The average number of different signs in a tweets.
 */
public class AvrSignsNumber extends SignAttribute {

    @Override
    public List<Double> apply(List<String> tweets) {
        List<Double> attrs = new ArrayList<>();
        double count = 0.;
        for (String tweet : tweets) {
            for (String sign : SIGNS) {
                if (tweet.contains(sign)) {
                    count++;
                }
            }
        }
        attrs.add(count / tweets.size());
        return attrs;
    }
}
