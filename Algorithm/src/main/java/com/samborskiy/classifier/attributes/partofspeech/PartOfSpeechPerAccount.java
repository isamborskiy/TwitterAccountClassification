package com.samborskiy.classifier.attributes.partofspeech;

import com.samborskiy.classifier.entities.sequences.PartOfSpeechSequence;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.PartOfSpeech;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Number of appearance part of speech in the tweets or a combination divided by the total number of tweets.
 */
public class PartOfSpeechPerAccount extends PartOfSpeechFunction {

    public PartOfSpeechPerAccount(PartOfSpeechSequence sequence) {
        super(sequence);
    }

    @Override
    public String getName() {
        return String.format("%s_per_tweet", sequence.toString());
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0;
        for (String tweet : tweets) {
            count += sequence.count(tweet);
        }
        attributes.add(new Attribute(count / tweets.size(), getName()));
    }
}
