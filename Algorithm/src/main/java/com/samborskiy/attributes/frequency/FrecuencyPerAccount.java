package com.samborskiy.attributes.frequency;

import com.samborskiy.attributes.AttributeFunction;
import com.samborskiy.entities.Attribute;

import java.util.List;

public class FrecuencyPerAccount extends AttributeFunction {

    @Override
    public String getName() {
        return "frequency_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double frequency = 0.;
        for (String tweet : tweets) {
            double tweetFrequency = 0.;
            List<String> words = tweetParser.parse(tweet);
            for (String word : words) {
                tweetFrequency += frequencyAnalyzer.getFrequency(word);
            }
            frequency += tweetFrequency / words.size();
        }
        attributes.add(new Attribute(frequency, getName()));
    }
}
