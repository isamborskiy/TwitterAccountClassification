package com.samborskiy.entities.analyzers.morphological;

import com.samborskiy.entities.PartOfSpeech;
import com.samborskiy.entities.analyzers.frequency.FrequencyDictionary;

/**
 * Created by Whiplash on 25.04.2015.
 */
public class FrequencyMorphologicalAnalyzer extends MorphologicalAnalyzer {

    private final FrequencyDictionary frequencyDictionary = new FrequencyDictionary();

    @Override
    public PartOfSpeech get(String word) {
        SimpleMorphologicalAnalyzer morphologicalAnalyzer = new SimpleMorphologicalAnalyzer();
        PartOfSpeech partOfSpeech = frequencyDictionary.getPartOfSpeech(word);
        if (partOfSpeech == null) {
            partOfSpeech = morphologicalAnalyzer.get(word);
        }
        return partOfSpeech;
    }
}
