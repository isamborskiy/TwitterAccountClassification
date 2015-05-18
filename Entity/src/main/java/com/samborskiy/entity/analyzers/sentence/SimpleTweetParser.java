package com.samborskiy.entity.analyzers.sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class SimpleTweetParser extends TweetParser {

    @Override
    public List<String> parse(String tweet) {
        StringTokenizer tokenizer = new StringTokenizer(tweet, "\',;:.!? \t\n\r\f");
        List<String> words = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            words.add(tokenizer.nextToken());
        }
        return words;
    }
}
