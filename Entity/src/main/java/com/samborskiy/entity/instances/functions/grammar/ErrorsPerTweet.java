package com.samborskiy.entity.instances.functions.grammar;

import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.instances.Attribute;
import com.samborskiy.entity.instances.functions.AttributeFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class ErrorsPerTweet extends AttributeFunction {
    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        GrammarAnalyzer grammarAnalyzer = GrammarAnalyzer.get();
        double count = 0.;
        for (String tweet : tweets) {
            try {
                count += grammarAnalyzer.check(tweet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        attributes.add(getAttribute(count / tweets.size()));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "errors_per_tweet");
    }
}
