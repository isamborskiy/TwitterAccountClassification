package com.samborskiy.entity.instances.attributes;

import java.util.ArrayList;
import java.util.List;

/**
 * The number of appearances of any sign divided by the total number of tweets.
 */
public class SingsPerTweet extends SignAttribute {

    @Override
    public List<Double> apply(List<String> tweets) {
        List<Double> attrs = new ArrayList<>();
        double count = 0;
        for (String sign : SIGNS) {
            int index = 0;
            for (String tweet : tweets) {
                while ((index = tweet.indexOf(sign, index)) != -1) {
                    count++;
                    index++;
                }
            }
        }
        attrs.add(count / tweets.size());
        return attrs;
    }
}
