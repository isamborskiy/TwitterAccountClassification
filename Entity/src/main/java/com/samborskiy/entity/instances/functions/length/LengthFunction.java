package com.samborskiy.entity.instances.functions.length;

import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.instances.Attribute;
import com.samborskiy.entity.instances.functions.AccountFunction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 27.04.2015.
 */
public abstract class LengthFunction extends AccountFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        TweetParser tweetParser = TweetParser.get();
        List<List<String>> tweetsList = tweets.stream().map(tweetParser::parse).collect(Collectors.toList());
        return count(tweetsList);
    }

    protected abstract List<Attribute> count(List<List<String>> tweets);
}
