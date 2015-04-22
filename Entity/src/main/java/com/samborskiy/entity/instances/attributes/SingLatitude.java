package com.samborskiy.entity.instances.attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Latitude author use punctuation, equal to the maximum on the
 * number of different signs in sentences, divided by the number
 * of possible characters.
 */
public class SingLatitude extends SignAttribute {

    @Override
    public List<Double> apply(List<String> tweets) {
        List<Double> attrs = new ArrayList<>();
        double max = 0;
        for (String tweet : tweets) {
            double count = 0.;
            for (String sign : SIGNS) {
                if (tweet.contains(sign)) {
                    count++;
                }
            }
            max = Math.max(max, count);
        }
        attrs.add(max / SIGNS.length);
        return attrs;
    }
}
