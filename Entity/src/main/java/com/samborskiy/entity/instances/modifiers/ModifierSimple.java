package com.samborskiy.entity.instances.modifiers;

import com.samborskiy.entity.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 10.04.2015.
 */
public class ModifierSimple extends Modifier {

    @Override
    public List<String> modifyTweet(String tweet, Language language) {
        List<String> words = new ArrayList<>();
        String[] wordsArray = tweet.split("[ ,.?!()-]");
        for (String word : wordsArray) {
            word = modifyWord(word, language);
            if (word != null) {
                words.add(word);
            }
        }
        return words;
    }

    protected String modifyWord(String word, Language language) {
        if (language.isCorrectWord(word)) {
            return word.toLowerCase();
        }
        return null;
    }
}
