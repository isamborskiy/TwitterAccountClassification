package com.samborskiy.entity.instances.functions.tweet.personal;

import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.instances.functions.tweet.TweetFunction;

import java.util.Arrays;

/**
 * Created by whiplash on 29.04.2015.
 */
public abstract class PersonalFunction extends TweetFunction {

    protected final String[] WORDS = {"я", "ты", "буду"};
    protected final String[] SUFFIXES = {"ил", "ыл", "ал", "ила", "ыла", "ала"};

    protected double match(String tweet) {
        TweetParser parser = TweetParser.get();
        double count = 0.;
        for (String word : parser.parse(tweet)) {
            if (Arrays.asList(WORDS).contains(word)) {
                count++;
                continue;
            }
            for (String suffix : SUFFIXES) {
                if (word.endsWith(suffix)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
