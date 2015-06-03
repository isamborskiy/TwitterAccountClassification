package com.samborskiy.classifier.attributes.link;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 04.06.2015.
 */
public class InstagramPerTweet extends LinkAttributeFunction {

    public InstagramPerTweet(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer, MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        float urlsPerTweet = 0;
        for (String tweet : tweets) {
            urlsPerTweet += instagramCount(tweet);
        }
        attributes.add(getAttribute(urlsPerTweet / tweets.size()));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "instagram_per_tweet");
    }
}
