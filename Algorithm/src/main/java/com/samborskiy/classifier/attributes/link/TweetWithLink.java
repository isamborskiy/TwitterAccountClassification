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
public class TweetWithLink extends LinkAttributeFunction {

    public TweetWithLink(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer, MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        float tweetsWithUrls = 0;
        for (String tweet : tweets) {
            tweetsWithUrls += urlsCount(tweet) > 0 ? 1 : 0;
        }
        attributes.add(getAttribute(tweetsWithUrls / tweets.size()));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "tweets_with_urls");
    }
}
