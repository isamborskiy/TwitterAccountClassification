package com.samborskiy.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Class stores user's tweet.
 *
 * @author Whiplash
 */
public class Tweet implements Iterable<String> {

    /**
     * Regex for parse url.
     */
    private static final String URL_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    private final List<String> words = new ArrayList<>();
    private final String tweet;
    private final int classId;

    /**
     * Returns new instance of {@code Message} parsing {@code tweet}.
     *
     * @param tweet         text of tweet
     * @param classId       id of class (eg. 0 is personal, 1 is corporate)
     * @param configuration configuration for chosen language
     */
    public Tweet(String tweet, int classId, Configuration configuration) {
        this.tweet = tweet;
        tweet = tweet.replaceAll(URL_REGEX, " ");
        String[] wordsArray = tweet.split("[ ,.?!()-]");
        for (String word : wordsArray) {
            if (isCorrect(word, configuration)) {
                this.words.add(word);
            }
        }
        this.classId = classId;
    }

    /**
     * Returns new instance of {@code Message} parsing {@code tweet} with {@code classId} = 0.
     *
     * @param tweet         text of tweet
     * @param configuration configuration for chosen language
     */
    public Tweet(String tweet, Configuration configuration) {
        this(tweet, 0, configuration);
    }

    /**
     * Checks is {@code word} correct or not.
     *
     * @param word          word to be checked
     * @param configuration configuration for chosen language
     * @return {@code true} if word is correct, {@code false} - otherwise
     */
    private boolean isCorrect(String word, Configuration configuration) {
        return word.length() >= 3 && configuration.getLang().isCorrectWord(word);
    }

    public int getClassId() {
        return classId;
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

    @Override
    public void forEach(Consumer<? super String> action) {
        words.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return words.spliterator();
    }
}
