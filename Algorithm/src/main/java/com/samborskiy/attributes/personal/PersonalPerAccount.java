package com.samborskiy.attributes.personal;

import com.samborskiy.entities.Attribute;

import java.util.List;

/**
 * Created by whiplash on 29.04.2015.
 */
public class PersonalPerAccount extends PersonalFunction {

    @Override
    public String getName() {
        return "personal_words_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0.;
        for (String tweet : tweets) {
            count += match(tweet);
        }
        attributes.add(new Attribute(count / tweets.size(), getName()));
    }
}
