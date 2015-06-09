package com.samborskiy.classifier.attributes.partofspeech;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.classifier.entities.sequences.PartOfSpeechSequence;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.PartOfSpeech;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.samborskiy.entity.PartOfSpeech.*;

/**
 * Created by Whiplash on 26.04.2015.
 */
public abstract class PartOfSpeechFunction extends AttributeFunction {

    public static final List<PartOfSpeechSequence> SEQUENCES = new ArrayList<>();

    static {
        SEQUENCES.add(new PartOfSpeechSequence("noun", NOUN));
        SEQUENCES.add(new PartOfSpeechSequence("verb", VERB));
        SEQUENCES.add(new PartOfSpeechSequence("personal_pronoun", PERSONAL_PRONOUN));
        SEQUENCES.add(new PartOfSpeechSequence("pronoun", PRONOUN));
        SEQUENCES.add(new PartOfSpeechSequence("adjective", ADJECTIVE));
        SEQUENCES.add(new PartOfSpeechSequence("adverb", ADVERB));
        SEQUENCES.add(new PartOfSpeechSequence("conjunction_preposition_particle", CONJUNCTION, PREPOSITION, PARTICLE));
        SEQUENCES.add(new PartOfSpeechSequence("adverb_adjective", ADVERB, ADJECTIVE));
        SEQUENCES.add(new PartOfSpeechSequence("adverb_adverb", ADVERB, ADVERB));
    }

    protected final PartOfSpeechSequence sequence;

    public PartOfSpeechFunction(PartOfSpeechSequence sequence) {
        this.sequence = sequence;
    }
}
