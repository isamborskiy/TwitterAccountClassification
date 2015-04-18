package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;

/**
 * Created by Whiplash on 10.04.2015.
 */
public class ModifierStemmer extends ModifierSimple {

    @Override
    public String modifyWord(String word, Language language) {
        if (language.isCorrectWord(word)) {
            return language.stem(word.toLowerCase());
        }
        return null;
    }
}
