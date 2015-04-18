package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;

import java.util.Iterator;
import java.util.List;

/**
 * Class stores user's tweet.
 *
 * @author Whiplash
 */
public class Tweet extends Instance {

    protected final List<String> words;
    protected final String tweet;

    /**
     * Returns new instance of {@code Message} parsing {@code tweet}.
     *
     * @param tweet    text of tweet
     * @param classId  id of class (eg. 0 is personal, 1 is corporate)
     * @param language language of tweet
     */
    public Tweet(String tweet, int classId, Language language, Modifier function) {
        super(classId);
        this.tweet = tweet;
        this.words = function.apply(tweet, language);
    }

    /**
     * Returns size of tweet.
     *
     * @return size of tweet
     */
    @Override
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
