package com.samborskiy.entity.instances.functions.partofspeech;

import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Number of tweets in which there is a certain part of speech  or a combination, divided by the total number of tweets.
 */
public class TweetsWithPartOfSpeech extends PartOfSpeechFunction {

    @Override
    protected List<Attribute> count(List<List<MorphologicalAnalyzer.PartOfSpeech>> partsOfSpeechList) {
        List<Attribute> attrs = new ArrayList<>();
        for (PartOfSpeechSequence sequence : SEQUENCES) {
            double count = 0;
            for (List<MorphologicalAnalyzer.PartOfSpeech> partsOfSpeech : partsOfSpeechList) {
                for (int i = 0; i < partsOfSpeech.size(); i++) {
                    if (sequence.match(partsOfSpeech, i)) {
                        count++;
                        break;
                    }
                }
            }
            attrs.add(getAttribute(count / partsOfSpeechList.size(), sequence.toString()));
        }
        return attrs;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, String.format("tweets_with_%s", args));
    }
}
