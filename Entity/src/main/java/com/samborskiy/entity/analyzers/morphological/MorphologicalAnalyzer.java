package com.samborskiy.entity.analyzers.morphological;

import com.samborskiy.entity.PartOfSpeech;

/**
 * Created by Whiplash on 25.04.2015.
 */
public abstract class MorphologicalAnalyzer {

    private static final MorphologicalAnalyzer analyzer = new SimpleMorphologicalAnalyzer();

    public abstract PartOfSpeech get(String word);

    public static MorphologicalAnalyzer get() {
        return analyzer;
    }
}
