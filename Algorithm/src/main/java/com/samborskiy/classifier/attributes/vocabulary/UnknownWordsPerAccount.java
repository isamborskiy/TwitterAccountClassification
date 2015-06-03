package com.samborskiy.classifier.attributes.vocabulary;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class UnknownWordsPerAccount extends AttributeFunction {

    private final MorphologicalAnalyzer morphologicalAnalyzer = MorphologicalAnalyzer.get();

    public UnknownWordsPerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                                  MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
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
        attributes.add(getAttribute(count / tweets.size()));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "unknown_word_per_tweet");
    }
}
