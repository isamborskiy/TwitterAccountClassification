package com.samborskiy.entity.instances.functions.tweet.length;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class AverageWordLength extends LengthFunction {

    @Override
    protected List<Attribute> count(List<List<String>> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        double lengthOfWords = 0.;
        int count = 0;
        for (List<String> tweet : tweets) {
            count += tweet.size();
            for (String word : tweet) {
                lengthOfWords += word.length();
            }
        }
        attributes.add(getAttribute(lengthOfWords / count));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "average_word_length");
    }
}
