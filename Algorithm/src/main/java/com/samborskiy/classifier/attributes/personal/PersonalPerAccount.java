package com.samborskiy.classifier.attributes.personal;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * Created by whiplash on 29.04.2015.
 */
public class PersonalPerAccount extends PersonalFunction {

    public PersonalPerAccount(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                              MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0.;
        for (String tweet : tweets) {
            count += match(tweet);
        }
        attributes.add(getAttribute(count / tweets.size()));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "personal_words_per_tweet");
    }
}
