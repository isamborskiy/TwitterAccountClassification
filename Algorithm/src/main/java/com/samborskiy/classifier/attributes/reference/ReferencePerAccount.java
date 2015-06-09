package com.samborskiy.classifier.attributes.reference;

import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class ReferencePerAccount extends ReferenceFunction {

    @Override
    public String getName() {
        return "reference_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        attributes.add(new Attribute(tweets.stream()
                .mapToDouble(t -> getReferences(t).size())
                .average().getAsDouble(), getName()));
    }
}
