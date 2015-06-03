package com.samborskiy.classifier.attributes.sign;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * The number of appearances sign in tweets divided by the total number of tweets.
 */
public class SignPerAccount extends SignFunction {

    public SignPerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                          MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        for (String sign : SIGNS.keySet()) {
            int index = 0;
            double count = 0;
            for (String tweet : tweets) {
                while ((index = tweet.indexOf(sign, index)) != -1) {
                    count++;
                    index++;
                }
            }
            attributes.add(getAttribute(count / tweets.size(), SIGNS.get(sign)));
        }
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, String.format("%s_per_tweet", args));
    }
}
