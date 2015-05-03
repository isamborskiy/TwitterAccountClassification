package com.samborskiy.entity.analyzers.morphological;

import com.samborskiy.entity.analyzers.frequency.FrequencyDictionary;

import java.io.IOException;

/**
 * Created by Whiplash on 25.04.2015.
 */
public class FrequencyMorphologicalAnalyzer implements MorphologicalAnalyzer {

    @Override
    public PartOfSpeech get(String word) {
        try {
            FrequencyDictionary frequencyDictionary = new FrequencyDictionary();
            SimpleMorphologicalAnalyzer morphologicalAnalyzer = new SimpleMorphologicalAnalyzer();
            PartOfSpeech partOfSpeech = frequencyDictionary.getPartOfSpeech(word);
            if (partOfSpeech == null) {
                partOfSpeech = morphologicalAnalyzer.get(word);
            }
            return partOfSpeech;
        } catch (IOException e) {
            return null;
        }
    }
}
