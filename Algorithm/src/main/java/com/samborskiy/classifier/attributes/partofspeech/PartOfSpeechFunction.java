package com.samborskiy.classifier.attributes.partofspeech;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.PartOfSpeech;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.samborskiy.entity.PartOfSpeech.*;

/**
 * Created by Whiplash on 26.04.2015.
 */
public abstract class PartOfSpeechFunction extends AttributeFunction {

    protected static final List<PartOfSpeechSequence> SEQUENCES = new ArrayList<>();

    static {
        SEQUENCES.add(new PartOfSpeechSequence(NOUN));
        SEQUENCES.add(new PartOfSpeechSequence(VERB));
        SEQUENCES.add(new PartOfSpeechSequence(PERSONAL_PRONOUN));
        SEQUENCES.add(new PartOfSpeechSequence(PRONOUN));
        SEQUENCES.add(new PartOfSpeechSequence(ADJECTIVE));
        SEQUENCES.add(new PartOfSpeechSequence(ADVERB));
        SEQUENCES.add(new PartOfSpeechSequence(CONJUNCTION, PREPOSITION, PARTICLE));
        SEQUENCES.add(new PartOfSpeechSequence(ADVERB, ADJECTIVE));
        SEQUENCES.add(new PartOfSpeechSequence(ADVERB, ADVERB));
    }

    public PartOfSpeechFunction(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                                MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<List<PartOfSpeech>> partsOfSpeechList = new ArrayList<>();
        for (String tweet : tweets) {
            partsOfSpeechList.add(tweetParser.parse(tweet).stream().map(word -> morphologicalAnalyzer.get(word.toLowerCase())).collect(Collectors.toList()));
        }
        return count(partsOfSpeechList);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        // empty...
    }

    protected abstract List<Attribute> count(List<List<PartOfSpeech>> partsOfSpeechList);

    protected static class PartOfSpeechSequence {

        private final List<PartOfSpeech> sequence;

        public PartOfSpeechSequence(PartOfSpeech... sequence) {
            this.sequence = Arrays.asList(sequence);
        }

        public boolean match(List<PartOfSpeech> tweet, int position) {
            if (tweet.size() - position < sequence.size()) {
                return false;
            }
            for (int i = position; i < position + sequence.size(); i++) {
                if (!sequence.get(i - position).equals(tweet.get(i))) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(sequence.get(0).toString());
            for (int i = 1; i < sequence.size(); i++) {
                builder.append("_").append(sequence.get(i).toString());
            }
            return builder.toString().toLowerCase();
        }
    }
}
