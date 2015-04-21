package com.samborskiy.entity.instances.modifiers;

import com.samborskiy.entity.Language;

import java.util.List;
import java.util.function.BiFunction;

public abstract class Modifier implements BiFunction<String, Language, List<String>> {
    
    protected static final String URL_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Override
    public List<String> apply(String tweet, Language language) {
        tweet = tweet.replaceAll(URL_REGEX, " ");
        return modifyTweet(tweet, language);
    }

    protected abstract List<String> modifyTweet(String tweet, Language language);
}
