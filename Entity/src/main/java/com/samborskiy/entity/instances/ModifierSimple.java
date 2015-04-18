package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 10.04.2015.
 */
public class ModifierSimple implements Modifier {

    protected static final String URL_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Override
    public List<String> apply(String tweet, Language language) {
        List<String> words = new ArrayList<>();
        tweet = tweet.replaceAll(URL_REGEX, " ");
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
