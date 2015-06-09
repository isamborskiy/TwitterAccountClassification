package com.samborskiy.attributes.link;

import com.samborskiy.entities.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 04.06.2015.
 */
public class LinkPerTweet extends LinkAttributeFunction {

    @Override
    public String getName() {
        return "urls_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        float urlsPerTweet = 0;
        for (String tweet : tweets) {
            urlsPerTweet += urlsCount(tweet);
        }
        attributes.add(new Attribute(urlsPerTweet / tweets.size(), getName()));
    }
}
