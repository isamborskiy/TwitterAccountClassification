package com.samborskiy.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class stores user's tweet.
 *
 * @author Whiplash
 */
public class Tweet extends Instance {

    /**
     * Regex for parse url.
     */
    private static final String URL_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    private final List<String> words;
    private final String tweet;

    /**
     * Returns new instance of {@code Message} parsing {@code tweet}.
     *
     * @param tweet    text of tweet
     * @param classId  id of class (eg. 0 is personal, 1 is corporate)
     * @param language language of tweet
     */
    public Tweet(String tweet, int classId, Language language) {
        super(classId);
        this.tweet = tweet;
        this.words = parseTweet(tweet, language);
    }

    /**
     * Parses tweet's string to get word from it.
     *
     * @param tweet    text of tweet
     * @param language language of tweet
     * @return tweet
     */
    private List<String> parseTweet(String tweet, Language language) {
        List<String> words = new ArrayList<>();
        tweet = tweet.replaceAll(URL_REGEX, " ");
        String[] wordsArray = tweet.split("[ ,.?!()-]");
        for (String word : wordsArray) {
            if (language.isCorrectWord(word)) {
                words.add(word);
            }
        }
        return words;
    }

    /**
     * Returns size of tweet.
     *
     * @return size of tweet
     */
    public int size() {
        return words.size();
    }

    /**
     * Returns {@code i}-th word of tweet.
     *
     * @param i index of the element to return
     * @return word of tweet
     */
    public String get(int i) {
        return words.get(i);
    }

    @Override
    public Iterator<String> iterator() {
        return words.iterator();
    }

}
