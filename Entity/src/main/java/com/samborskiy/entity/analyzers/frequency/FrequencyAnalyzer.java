package com.samborskiy.entity.analyzers.frequency;

import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer.PartOfSpeech;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Whiplash on 03.05.2015.
 */
public abstract class FrequencyAnalyzer {

    private static final FrequencyAnalyzer analyzer = new FrequencyDictionary();

    public abstract double getFrequency(String word);

    public static FrequencyAnalyzer get() {
        return analyzer;
    }

    public static class WordFrequency {

        private static final Map<String, PartOfSpeech> abbreviateToClass = new HashMap<>();

        static {
            abbreviateToClass.put("noun", PartOfSpeech.NOUN);
            abbreviateToClass.put("verb", PartOfSpeech.VERB);
            abbreviateToClass.put("adj", PartOfSpeech.ADJECTIVE);
            abbreviateToClass.put("ord", PartOfSpeech.NUMERAL);
            abbreviateToClass.put("card", PartOfSpeech.NUMERAL);
            abbreviateToClass.put("adv", PartOfSpeech.ADVERB);
            abbreviateToClass.put("pron", PartOfSpeech.PRONOUN);
            abbreviateToClass.put("adjpron", PartOfSpeech.PRONOUN);
            abbreviateToClass.put("misc", PartOfSpeech.PARTICLE);
        }

        private final double frequency;
        private final String word;
        private final PartOfSpeech partOfSpeech;

        public WordFrequency(double frequency, String word, PartOfSpeech partOfSpeech) {
            this.frequency = frequency;
            this.word = word;
            this.partOfSpeech = partOfSpeech;
        }

        public WordFrequency(double frequency, String word, String partOfSpeech) {
            this(frequency, word, abbreviateToClass.get(partOfSpeech));
        }

        public double getFrequency() {
            return frequency;
        }

        public PartOfSpeech getPartOfSpeech() {
            return partOfSpeech;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WordFrequency that = (WordFrequency) o;
            return !(word != null ? !word.equals(that.word) : that.word != null);

        }

        @Override
        public int hashCode() {
            return word != null ? word.hashCode() : 0;
        }
    }
}
