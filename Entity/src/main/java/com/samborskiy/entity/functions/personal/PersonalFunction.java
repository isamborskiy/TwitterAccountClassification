package com.samborskiy.entity.functions.personal;

import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.functions.AccountFunction;

import java.util.Arrays;

/**
 * Created by whiplash on 29.04.2015.
 */
public abstract class PersonalFunction extends AccountFunction {

    //    protected final String[] WORDS = {"я", "ты", "буду", "мой", "мне"};
//    protected final String[] SUFFIXES = {"ил", "ыл", "ал", "ила", "ыла", "ала"};
//    protected final String[] PREFIXES = {};
    protected final String[] WORDS = {"I", "i"};
    protected final String[] SUFFIXES = {};
    protected final String[] PREFIXES = {"fuck"};

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
            for (String prefix : PREFIXES) {
                if (word.startsWith(prefix)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
