package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Class stores user's tweet.
 *
 * @author Whiplash
 */
public class TweetSimple extends Tweet {

    /**
     * Regex for parse url.
     */
    private static final String URL_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    /**
     * Returns new instance of {@code Message} parsing {@code tweet}.
     *
     * @param tweet    text of tweet
     * @param classId  id of class (eg. 0 is personal, 1 is corporate)
     * @param language language of tweet
     */
    public TweetSimple(String tweet, int classId, Language language, WordModify function) {
        super(tweet, classId, language, function);
    }

    /**
     * Parses tweet's string to get word from it.
     *
     * @param tweet    text of tweet
     * @param language language of tweet
     * @return tweet
     */
    @Override
    protected List<String> parseTweet(String tweet, Language language) {
        List<String> words = new ArrayList<>();
        tweet = tweet.replaceAll(URL_REGEX, " ");
        String[] wordsArray = tweet.split("[ ,.?!()-]");
        for (String word : wordsArray) {
            word = function.apply(word, language);
            if (word != null) {
                words.add(word);
            }
        }
        return words;
    }
}
