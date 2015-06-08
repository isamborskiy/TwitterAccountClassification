package com.samborskiy.classifier.attributes.sign;

import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * The number of tweets where is find sign divided by the total number of tweets.
 */
public class TweetsWithSign extends SignFunction {

    public TweetsWithSign(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                          MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser, String... args) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser, args);
    }

    @Override
    public String getName() {
        return String.format("tweets_with_%s", args);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        for (String sign : SIGNS.keySet()) {
            double count = 0;
            for (String tweet : tweets) {
                if (tweet.contains(sign)) {
                    count++;
                }
            }
            attributes.add(new Attribute(count / tweets.size(), getName()));
        }
    }
}
