package com.samborskiy.classifier.attributes.partofspeech;

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

    public PartOfSpeechPerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                                  MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser, String... args) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser, args);
    }

    @Override
    public String getName() {
        return String.format("%s_per_tweet", args);
    }

    @Override
    protected List<Attribute> count(List<List<PartOfSpeech>> partsOfSpeechList) {
        List<Attribute> attrs = new ArrayList<>();
        for (PartOfSpeechSequence sequence : SEQUENCES) {
            double count = 0;
            for (List<PartOfSpeech> partsOfSpeech : partsOfSpeechList) {
                for (int i = 0; i < partsOfSpeech.size(); i++) {
                    count += sequence.match(partsOfSpeech, i) ? 1 : 0;
                }
            }
            attrs.add(new Attribute(count / partsOfSpeechList.size(), getName()));
        }
        return attrs;
    }
}
