package com.samborskiy.entity.instances.attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * The number of appearances sign in tweets divided by the total number of tweets.
 */
public class SignPerTweet extends SignAttribute {

    @Override
    public List<Double> apply(List<String> tweets) {
        List<Double> attrs = new ArrayList<>();
        for (String sign : SIGNS) {
            int index = 0;
            double count = 0;
            for (String tweet : tweets) {
                while ((index = tweet.indexOf(sign, index)) != -1) {
                    count++;
                    index++;
                }
            }
            attrs.add(count / tweets.size());
        }
        return attrs;
    }
}
