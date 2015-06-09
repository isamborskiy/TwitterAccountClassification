package com.samborskiy.classifier.attributes.sign;

import com.samborskiy.classifier.entities.sequences.SignSequence;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * The number of appearances sign in tweets divided by the total number of tweets.
 */
public class SignPerAccount extends SignFunction {

    private final SignSequence sequence;

    public SignPerAccount(SignSequence sequence) {
        this.sequence = sequence;
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
