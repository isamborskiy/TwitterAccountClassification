package com.samborskiy.entity.instances;

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
public class AccountWithTweet extends Instance {

    private final List<String> words;
    private final List<Tweet> tweets;

    /**
     * Creates new instance of {@code Account} with init {@code classId},
     * and {@code language}.
     *
     * @param classId id of class (eg. 0 is personal, 1 is corporate)
     */
    public AccountWithTweet(int classId) {
        super(classId);
        this.words = new ArrayList<>();
        this.tweets = new ArrayList<>();
    }

    /**
     * Adds new tweet of account to collection.
     *
     * @param tweet tweet to be appended to this list
     */
    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
        for (String word : tweet) {
            words.add(word);
        }
    }

    /**
     * Appends all of tweets to collection.
     *
     * @param tweets collection of tweets to be added
     */
    public void addAll(List<Tweet> tweets) {
        tweets.forEach(this::addTweet);
    }

    @Override
    public Iterator<String> iterator() {
        return words.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        words.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return words.spliterator();
    }

    @Override
    public int size() {
        return words.size();
    }

    public int tweetNumber() {
        return tweets.size();
    }

    public Tweet getTweet(int index) {
        return tweets.get(index);
    }
}
