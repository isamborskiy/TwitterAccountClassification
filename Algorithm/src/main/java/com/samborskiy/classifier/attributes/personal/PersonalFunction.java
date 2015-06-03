package com.samborskiy.classifier.attributes.personal;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;

import java.util.Arrays;

/**
 * Created by whiplash on 29.04.2015.
 */
public abstract class PersonalFunction extends AttributeFunction {

    protected final String[] WORDS = {"я", "ты", "буду", "мой", "мне"};
    protected final String[] SUFFIXES = {"ил", "ыл", "ал", "ила", "ыла", "ала"};

    public PersonalFunction(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                            MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    protected double match(String tweet) {
        double count = 0.;
        for (String word : tweetParser.parse(tweet)) {
            if (Arrays.asList(WORDS).contains(word)) {
                count++;
                continue;
            }
            for (String suffix : SUFFIXES) {
                if (word.endsWith(suffix)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
