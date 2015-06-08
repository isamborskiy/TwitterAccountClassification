package com.samborskiy.classifier.attributes.hashtag;

import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class HashTagLength extends HashTagFunction {

    public HashTagLength(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                         MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser, String... args) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser, args);
    }

    @Override
    public String getName() {
        return "hash_tag_length";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double length = 0.;
        for (String tweet : tweets) {
            for (String hashTag : getHashTags(tweet)) {
                length += hashTag.length();
            }
        }
        attributes.add(new Attribute(length / tweets.size(), getName()));
    }
}
