package com.samborskiy.classifier.attributes.reference;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class ReferencePerAccount extends ReferenceFunction {

    public ReferencePerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                               MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        attributes.add(getAttribute(tweets.stream()
                .mapToDouble(t -> getReferences(t).size())
                .average().getAsDouble()));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "reference_per_tweet");
    }
}
