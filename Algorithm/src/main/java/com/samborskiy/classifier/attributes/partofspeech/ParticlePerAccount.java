package com.samborskiy.classifier.attributes.partofspeech;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * Number of appearance particle 'не' in the tweets divided by the total number of tweets.
 */
public class ParticlePerAccount extends AttributeFunction {

    private static final String NEGATIVE_PARTICLE = "не";

    public ParticlePerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                              MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser, String... args) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser, args);
    }

    @Override
    public String getName() {
        return "particle_ne_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0.;
        for (String tweet : tweets) {
            int index = 0;
            while ((index = tweet.indexOf(NEGATIVE_PARTICLE, index)) != -1) {
                count++;
                index++;
            }
        }
        attributes.add(new Attribute(count / tweets.size(), getName()));
    }
}
