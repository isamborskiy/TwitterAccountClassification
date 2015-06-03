package com.samborskiy.classifier.attributes.length;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class AverageWordLength extends LengthFunction {

    public AverageWordLength(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                             MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected List<Attribute> count(List<List<String>> tweets) {
        List<Attribute> attributes = new ArrayList<>();
        double lengthOfWords = 0.;
        int count = 0;
        for (List<String> tweet : tweets) {
            count += tweet.size();
            for (String word : tweet) {
                lengthOfWords += word.length();
            }
        }
        attributes.add(getAttribute(lengthOfWords / count));
        return attributes;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "average_word_length");
    }
}
