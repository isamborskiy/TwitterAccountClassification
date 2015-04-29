package com.samborskiy.entity.instances;

import com.samborskiy.entity.instances.functions.tweet.TweetFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 21.04.2015.
 */
public class Account extends Instance {

    protected static final String URL_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    protected final List<String> tweets;

    public Account(int classId) {
        super(classId);
        tweets = new ArrayList<>();
    }

    public int size() {
        return tweets.size();
    }

    public String get(int i) {
        return tweets.get(i);
    }

    @Override
    public void addAttr(TweetFunction tweetFunction) {
        attrs.addAll(tweetFunction.apply(tweets));
    }

    public void addTweet(String tweet) {
        tweets.add(tweet.replaceAll(URL_REGEX, " "));
    }

    public void addTweets(List<String> tweets) {
        tweets.forEach(this::addTweet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return !(tweets != null ? !tweets.equals(account.tweets) : account.tweets != null);
    }

    @Override
    public int hashCode() {
        return tweets != null ? tweets.hashCode() : 0;
    }
}
