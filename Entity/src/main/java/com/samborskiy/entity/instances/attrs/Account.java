package com.samborskiy.entity.instances.attrs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 21.04.2015.
 */
public abstract class Account extends Instance {

    protected static final String URL_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    protected final List<String> tweets;

    public Account(int classId) {
        super(classId);
        tweets = new ArrayList<>();
    }

    public void addTweet(String tweet) {
        tweets.add(tweet.replaceAll(URL_REGEX, " "));
    }

    public void addTweets(List<String> tweets) {
        tweets.forEach(this::addTweet);
    }

    public abstract void evalAttrs();
}
