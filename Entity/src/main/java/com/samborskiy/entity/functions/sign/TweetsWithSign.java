package com.samborskiy.entity.functions.sign;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * The number of tweets where is find sign divided by the total number of tweets.
 */
public class TweetsWithSign extends SignFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attrs = new ArrayList<>();
        for (String sign : SIGNS.keySet()) {
            double count = 0;
            for (String tweet : tweets) {
                if (tweet.contains(sign)) {
                    count++;
                }
            }
            attrs.add(getAttribute(count / tweets.size(), SIGNS.get(sign)));
        }
        return attrs;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, String.format("tweets_with_%s", args));
    }
}
