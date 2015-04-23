package com.samborskiy.entity.instances.functions;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * The number of tweets where is find any sign divided by the total number of tweets.
 */
public class TweetsWithSigns extends SignAttributeFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attrs = new ArrayList<>();
        double count = 0;
        for (String tweet : tweets) {
            for (String sign : SIGNS.keySet()) {
                if (tweet.contains(sign)) {
                    count++;
                    break;
                }
            }
        }
        attrs.add(getAttribute(count / tweets.size()));
        return attrs;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "tweets_with_signs");
    }
}
