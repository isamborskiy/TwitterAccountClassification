package com.samborskiy.classifier.attributes.hashtag;

import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class DifferentHashTags extends HashTagFunction {

    public DifferentHashTags(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                             MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser, String... args) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser, args);
    }

    @Override
    public String getName() {
        return "different_hash_tags_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        Set<String> hashTags = new HashSet<>();
        for (String tweet : tweets) {
            hashTags.addAll(getHashTags(tweet.toLowerCase()).stream().collect(Collectors.toList()));
        }
        attributes.add(new Attribute(((double) hashTags.size()) / tweets.size(), getName()));
    }
}
