package com.samborskiy.entity.instances.functions.sign;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * The number of appearances sign in tweets divided by the total number of tweets.
 */
public class SignPerTweet extends SignFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attrs = new ArrayList<>();
        for (String sign : SIGNS.keySet()) {
            int index = 0;
            double count = 0;
            for (String tweet : tweets) {
                while ((index = tweet.indexOf(sign, index)) != -1) {
                    count++;
                    index++;
                }
            }
            attrs.add(getAttribute(count / tweets.size(), SIGNS.get(sign)));
        }
        return attrs;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, String.format("%s_per_tweet", args));
    }
}
