package com.samborskiy.classifier.attributes.frequency;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

public class FrecuencyPerAccount extends AttributeFunction {

    public FrecuencyPerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                               MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser, String... args) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser, args);
    }

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
