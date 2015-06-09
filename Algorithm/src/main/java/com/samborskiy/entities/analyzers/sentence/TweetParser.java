package com.samborskiy.entities.analyzers.sentence;

import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public abstract class TweetParser {

    private static final TweetParser parser = new SimpleTweetParser();

    public abstract List<String> parse(String tweet);

    public static TweetParser get() {
        return parser;
    }
}
