package com.samborskiy.classifier.attributes.grammar;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.List;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class ErrorsPerAccount extends AttributeFunction {

    @Override
    public String getName() {
        return "errors_per_tweet";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0.;
        for (String tweet : tweets) {
            try {
                count += grammarAnalyzer.check(tweet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        attributes.add(new Attribute(count / tweets.size(), getName()));
    }
}
