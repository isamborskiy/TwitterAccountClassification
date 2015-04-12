package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;

/**
 * Created by Whiplash on 10.04.2015.
 */
public class TweetModifierLength extends TweetModifierSimple {

    @Override
    public String modifyWord(String word, Language language) {
        if (language.isCorrectWord(word)) {
            return String.valueOf(word.length());
        }
        return null;
    }
}
