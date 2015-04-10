package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;

/**
 * Created by Whiplash on 10.04.2015.
 */
public class WordModifySimple implements WordModify {

    @Override
    public String apply(String word, Language language) {
        if (language.isCorrectWord(word)) {
            return word.toLowerCase();
        }
        return null;
    }
}
