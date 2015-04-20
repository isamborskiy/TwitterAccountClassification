package com.samborskiy.entity.instances.modifiers;

import com.samborskiy.entity.Language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Whiplash on 10.04.2015.
 */
public class ModifierSigns extends ModifierSmiles {

    private static final String[] SIGNS = {"`", "~", "!", "@", "#", "$", "%",
            "^", "&", "*", "(", ")", "-", "_", "+", "=", "[", "{", "]", "}",
            "\\", "|", ";", ":", "'", "\"", ",", "<", ".", ">", "/", "?", "№"};

    @Override
    public List<String> apply(String tweet, Language language) {
        List<String> words = new ArrayList<>();
        List<String> smiles = getDelimiters();
        for (String smile : smiles) {
            int pos = 0;
            while ((pos = tweet.indexOf(smile, pos)) != -1) {
                words.add(smile);
                pos++;
            }
            tweet = tweet.replace(smile, " ");
        }
        return words;
    }

    @Override
    protected List<String> getDelimiters() {
        return Arrays.asList(SIGNS);
    }
}
