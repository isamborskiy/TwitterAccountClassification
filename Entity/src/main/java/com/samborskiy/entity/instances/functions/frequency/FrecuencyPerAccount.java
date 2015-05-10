package com.samborskiy.entity.instances.functions.frequency;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.instances.Attribute;
import com.samborskiy.entity.instances.functions.AccountFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 03.05.2015.
 */
public class FrecuencyPerAccount extends AccountFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.get();
        TweetParser parser = TweetParser.get();
        double frequency = 0.;
        for (String tweet : tweets) {
            double tweetFrequency = 0.;
            List<String> words = parser.parse(tweet);
            for (String word : words) {
                tweetFrequency += frequencyAnalyzer.getFrequency(word);
            }
            frequency += tweetFrequency / words.size();
        }
        attributes.add(getAttribute(frequency / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "frequency_per_tweet");
    }
}
