package com.samborskiy.classifier.attributes.sign;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * The average number of different signs in a tweets.
 */
public class AvrSignsNumber extends SignFunction {

    public AvrSignsNumber(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                          MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0.;
        for (String tweet : tweets) {
            for (String sign : SIGNS.keySet()) {
                if (tweet.contains(sign)) {
                    count++;
                }
            }
        }
        attributes.add(getAttribute(count / tweets.size()));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "average_signs_number");
    }
}
