package com.samborskiy.entity.analyzers.morphological;

/**
 * Created by Whiplash on 25.04.2015.
 */
public abstract class MorphologicalAnalyzer {

    private static final MorphologicalAnalyzer analyzer = new SimpleMorphologicalAnalyzer();

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

    public abstract PartOfSpeech get(String word);

    public static MorphologicalAnalyzer get() {
        return analyzer;
    }
}
