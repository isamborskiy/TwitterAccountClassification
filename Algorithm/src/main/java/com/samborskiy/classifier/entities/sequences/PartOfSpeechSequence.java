package com.samborskiy.classifier.entities.sequences;

import com.samborskiy.classifier.misc.ClassifierProperty;
import com.samborskiy.entity.PartOfSpeech;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whiplash on 08.06.15.
 */
public class PartOfSpeechSequence extends Sequence<PartOfSpeech> {

    public PartOfSpeechSequence(String name, PartOfSpeech... sequence) {
        super(name, sequence);
    }

    @Override
    public int count(String tweet) {
        MorphologicalAnalyzer morphologicalAnalyzer = ClassifierProperty.getMorphologicalAnalyzer();
        List<String> parsedTweet = ClassifierProperty.getTweetParser().parse(tweet);
        List<PartOfSpeech> partsOfSpeech = new ArrayList<>();
        for (String word : parsedTweet) {
            PartOfSpeech partOfSpeech = morphologicalAnalyzer.get(word);
            if (partOfSpeech != null) {

            }
        }

        return 0;
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
