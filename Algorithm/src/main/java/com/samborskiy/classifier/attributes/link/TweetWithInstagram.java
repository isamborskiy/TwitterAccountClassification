package com.samborskiy.classifier.attributes.link;

import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * Created by Whiplash on 04.06.2015.
 */
public class TweetWithInstagram extends LinkAttributeFunction {

    @Override
    public String getName() {
        return "tweets_with_instagram";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        float tweetsWithUrls = 0;
        for (String tweet : tweets) {
            tweetsWithUrls += instagramCount(tweet) > 0 ? 1 : 0;
        }
        attributes.add(new Attribute(tweetsWithUrls / tweets.size(), getName()));
    }
}
