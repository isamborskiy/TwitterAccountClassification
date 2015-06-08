package com.samborskiy.classifier.attributes.vocabulary;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class UnknownWordsPerAccount extends AttributeFunction {

    public UnknownWordsPerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                                  MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser, String... args) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser, args);
    }

    @Override
    public String getName() {
        return "unknown_word_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0.;
        for (String tweet : tweets) {
            for (String word : tweetParser.parse(tweet)) {
                if (morphologicalAnalyzer.get(word) == null) {
                    count++;
                }
            }
        }
        attributes.add(new Attribute(count / tweets.size(), getName()));
    }
}
