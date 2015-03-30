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
public class Account extends Instance {

    private final List<String> words;
    private final Language language;

    /**
     * Creates new instance of {@code Account} with init {@code classId},
     * and {@code language}.
     *
     * @param classId  id of class (eg. 0 is personal, 1 is corporate)
     * @param language language of tweet
     */
    public Account(int classId, Language language) {
        super(classId);
        this.words = new ArrayList<>();
        this.language = language;
    }

    /**
     * Adds new tweet of account to collection.
     *
     * @param tweet tweet to be appended to this list
     */
    public void addTweet(Tweet tweet) {
        for (String word : tweet) {
            words.add(word);
        }
    }

    /**
     * Adds new tweet of account to collection.
     *
     * @param tweet tweet to be appended to this list
     */
    public void addTweet(String tweet) {
        addTweet(new Tweet(tweet, classId, language));
    }


    /**
     * Appends all of tweets to collection.
     *
     * @param tweet collection of tweets to be added
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
}
