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
public class FrecuencyPerWord extends AccountFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.get();
        TweetParser parser = TweetParser.get();
        double frequency = 0.;
        double wordCount = 0.;
        for (String tweet : tweets) {
            List<String> words = parser.parse(tweet);
            for (String word : words) {
                frequency += frequencyAnalyzer.getFrequency(word);
            }
            wordCount += words.size();
        }
        attributes.add(getAttribute(frequency / wordCount));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "frequency_per_word");
    }
}
