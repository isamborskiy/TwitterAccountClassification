package com.samborskiy.classifier.attributes.smile;

import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class SmilePerAccount extends SmileFunction {

    public SmilePerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                           MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser, String... args) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser, args);
    }

    @Override
    public String getName() {
        return String.format("%s_per_tweet", args);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        for (SmileSequence smile : SMILES) {
            double count = 0;
            for (String tweet : tweets) {
                for (int i = 0; i < tweet.length(); i++) {
                    count += smile.match(tweet, i) ? 1 : 0;
                }
            }
            attributes.add(new Attribute(count / tweets.size(), getName()));
        }
    }
}
