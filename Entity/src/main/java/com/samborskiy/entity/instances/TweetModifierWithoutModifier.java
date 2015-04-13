package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.ArrayList;
import java.util.List;

public class TweetModifierWithoutModifier implements TweetModifier {

    @Override
    public List<String> apply(String tweet, @Ignore Language language) {
        List<String> words = new ArrayList<>();
        words.add(tweet);
        return words;
    }
}
