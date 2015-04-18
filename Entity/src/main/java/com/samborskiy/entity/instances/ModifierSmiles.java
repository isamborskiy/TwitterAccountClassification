package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 10.04.2015.
 */
public class ModifierSmiles implements Modifier {

    protected static final File SMILES_FILE = new File("res/smiles");

    @Override
    public List<String> apply(String tweet, Language language) {
        List<String> words = new ArrayList<>();
        List<String> smiles = getSmiles();
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

    private List<String> getSmiles() {
        List<String> smiles = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(SMILES_FILE))) {
            String line;
            while ((line = bf.readLine()) != null) {
                smiles.add(line);
            }
        } catch (IOException ignored) {
        }
        return smiles;
    }
}
