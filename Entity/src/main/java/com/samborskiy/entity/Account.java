package com.samborskiy.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Class stores user's tweets.
 *
 * @author Whiplash
 */
public class Account implements Iterable<Tweet> {

    private final List<Tweet> tweets;
    private final int classId;
    private final Language language;


    /**
     * Creates new instance of {@code Account} with init {@code classId},
     * and {@code language}.
     *
     * @param classId  id of class (eg. 0 is personal, 1 is corporate)
     * @param language language of tweet
     */
    public Account(int classId, Language language) {
        this.tweets = new ArrayList<>();
        this.classId = classId;
        this.language = language;
    }

    /**
     * Adds new tweet of account to collection.
     *
     * @param tweet tweet to be appended to this list
     */
    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    /**
     * Adds new tweet of account to collection.
     *
     * @param tweet tweet to be appended to this list
     */
    public void addTweet(String tweet) {
        tweets.add(new Tweet(tweet, classId, language));
    }


    /**
     * Appends all of tweets to collection.
     *
     * @param tweet collection of tweets to be added
     */
    public void addAll(List<Tweet> tweets) {
        this.tweets.addAll(tweets);
    }

    @Override
    public Iterator<Tweet> iterator() {
        return tweets.iterator();
    }

    @Override
    public void forEach(Consumer<? super Tweet> action) {
        tweets.forEach(action);
    }

    @Override
    public Spliterator<Tweet> spliterator() {
        return tweets.spliterator();
    }
}
