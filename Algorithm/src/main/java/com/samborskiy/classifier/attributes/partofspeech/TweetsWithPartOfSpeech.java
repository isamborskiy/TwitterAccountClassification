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
 * Number of tweets in which there is a certain part of speech or a combination, divided by the total number of tweets.
 */
public class TweetsWithPartOfSpeech extends PartOfSpeechFunction {

    public TweetsWithPartOfSpeech(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                                  MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser, String... args) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser, args);
    }

    @Override
    public String getName() {
        return String.format("tweets_with_%s", args);
    }

    @Override
    protected List<Attribute> count(List<List<PartOfSpeech>> partsOfSpeechList) {
        List<Attribute> attrs = new ArrayList<>();
        for (PartOfSpeechSequence sequence : SEQUENCES) {
            double count = 0;
            for (List<PartOfSpeech> partsOfSpeech : partsOfSpeechList) {
                for (int i = 0; i < partsOfSpeech.size(); i++) {
                    if (sequence.match(partsOfSpeech, i)) {
                        count++;
                        break;
                    }
                }
            }
            attrs.add(new Attribute(count / partsOfSpeechList.size(), getName()));
        }
        return attrs;
    }
}
