package com.samborskiy.entity.instances.functions.tweet.personal;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whiplash on 29.04.2015.
 */
public class PersonalPerTweet extends PersonalFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        double count = 0.;
        for (String tweet : tweets) {
            count += match(tweet);
        }
        attributes.add(getAttribute(count / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "personal_words_per_tweet");
    }
}
