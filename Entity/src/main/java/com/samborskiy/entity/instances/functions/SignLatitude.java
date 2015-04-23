package com.samborskiy.entity.instances.functions;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Latitude author use punctuation, equal to the maximum on the
 * number of different signs in sentences, divided by the number
 * of possible characters.
 */
public class SignLatitude extends SignAttributeFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attrs = new ArrayList<>();
        double max = 0;
        for (String tweet : tweets) {
            double count = 0.;
            for (String sign : SIGNS.keySet()) {
                if (tweet.contains(sign)) {
                    count++;
                }
            }
            max = Math.max(max, count);
        }
        attrs.add(getAttribute(max / SIGNS.size()));
        return attrs;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "sign_latitude");
    }
}
