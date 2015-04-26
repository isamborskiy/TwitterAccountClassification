package com.samborskiy.entity.analyzers.sentence;

import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public interface TweetParser {

    public List<String> parse(String tweet);

    public static TweetParser get() {
        return new SimpleTweetParser();
    }
}
