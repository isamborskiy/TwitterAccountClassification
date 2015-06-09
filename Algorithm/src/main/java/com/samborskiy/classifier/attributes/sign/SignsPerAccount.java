package com.samborskiy.classifier.attributes.sign;

import com.samborskiy.classifier.entities.sequences.SignSequence;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * The number of appearances of any sign divided by the total number of tweets.
 */
public class SignsPerAccount extends SignFunction {

    @Override
    public String getName() {
        return "signs_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0;
        for (SignSequence sequence : SIGNS) {
            for (String tweet : tweets) {
                count += sequence.count(tweet);
            }
        }
        attributes.add(new Attribute(count / tweets.size(), getName()));
    }
}
