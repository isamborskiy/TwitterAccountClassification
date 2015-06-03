package com.samborskiy.classifier.attributes.sign;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * Latitude author use punctuation, equal to the maximum on the
 * number of different signs in sentences, divided by the number
 * of possible characters.
 */
public class SignLatitude extends SignFunction {

    public SignLatitude(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                        MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double max = 0;
        for (String tweet : tweets) {
            double count = 0.;
            for (String sign : SIGNS.keySet()) {
                if (tweet.contains(sign)) {
                    count++;
                }
            }
            max = Math.max(max, count);
        }
        attributes.add(getAttribute(max / SIGNS.size()));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "sign_latitude");
    }
}
