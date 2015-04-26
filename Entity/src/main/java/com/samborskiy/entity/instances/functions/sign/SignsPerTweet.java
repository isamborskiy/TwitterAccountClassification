package com.samborskiy.entity.instances.functions.sign;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * The number of appearances of any sign divided by the total number of tweets.
 */
public class SignsPerTweet extends SignFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attrs = new ArrayList<>();
        double count = 0;
        for (String sign : SIGNS.keySet()) {
            int index = 0;
            for (String tweet : tweets) {
                while ((index = tweet.indexOf(sign, index)) != -1) {
                    count++;
                    index++;
                }
            }
        }
        attrs.add(getAttribute(count / tweets.size()));
        return attrs;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "signs_per_tweet");
    }
}
