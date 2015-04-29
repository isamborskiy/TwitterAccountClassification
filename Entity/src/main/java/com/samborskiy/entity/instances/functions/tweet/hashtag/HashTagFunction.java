package com.samborskiy.entity.instances.functions.tweet.hashtag;

import com.samborskiy.entity.instances.functions.tweet.TweetFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Whiplash on 28.04.2015.
 */
public abstract class HashTagFunction extends TweetFunction {

    protected List<String> getHashTags(String tweet) {
        Pattern pattern = Pattern.compile("#(\\w)+");
        Matcher matcher = pattern.matcher(tweet);
        List<String> hashTags = new ArrayList<>();
        while (matcher.find()) {
            hashTags.add(matcher.group(0));
        }
        return hashTags;
    }
}
