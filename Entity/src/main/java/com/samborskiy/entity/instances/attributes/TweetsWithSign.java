package com.samborskiy.entity.instances.attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * The number of tweets where is find sign divided by the total number of tweets.
 */
public class TweetsWithSign extends SignAttribute {

    @Override
    public List<Double> apply(List<String> tweets) {
        List<Double> attrs = new ArrayList<>();
        for (String sign : SIGNS) {
            double count = 0;
            for (String tweet : tweets) {
                if (tweet.contains(sign)) {
                    count++;
                }
            }
            attrs.add(count / tweets.size());
        }
        return attrs;
    }
}
