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
public class ReferenceLength extends ReferenceFunction {

    @Override
    public String getName() {
        return "reference_length";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double length = 0.;
        for (String tweet : tweets) {
            for (String reference : getReferences(tweet)) {
                length += reference.length();
            }
        }
        attributes.add(new Attribute(length / tweets.size(), getName()));
    }
}
