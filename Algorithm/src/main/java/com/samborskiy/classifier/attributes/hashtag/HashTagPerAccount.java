package com.samborskiy.classifier.attributes.hashtag;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class HashTagPerAccount extends HashTagFunction {

    public HashTagPerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                             MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        attributes.add(getAttribute(tweets.stream()
                .mapToDouble(t -> getHashTags(t).size())
                .average().getAsDouble()));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "hash_tag_per_tweet");
    }
}
