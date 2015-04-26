package com.samborskiy.entity.analyzers.morphological;

/**
 * Created by Whiplash on 25.04.2015.
 */
public interface MorphologicalAnalyzer {

    public enum PartOfSpeech {
        ADJECTIVE,
        COMMUNION,
        VERB,
        NOUN,
        ADVERB,
        NUMERAL,
        CONJUNCTION,
        PREPOSITION,
        PERSONAL_PRONOUN,
        PRONOUN,
        PARTICLE
    }

    public PartOfSpeech get(String word);
}
