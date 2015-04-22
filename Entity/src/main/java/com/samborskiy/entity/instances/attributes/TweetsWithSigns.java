package com.samborskiy.entity.instances.attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * The number of tweets where is find any sign divided by the total number of tweets.
 */
public class TweetsWithSigns extends SignAttribute {

    @Override
    public List<Double> apply(List<String> tweets) {
        List<Double> attrs = new ArrayList<>();
        double count = 0;
        for (String tweet : tweets) {
            for (String sign : SIGNS) {
                if (tweet.contains(sign)) {
                    count++;
                    break;
                }
            }
        }
        attrs.add(count / tweets.size());
        return attrs;
    }
}
