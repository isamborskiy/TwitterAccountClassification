package com.samborskiy.classifier.attributes.frequency;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

public class FrecuencyPerWord extends AttributeFunction {

    public FrecuencyPerWord(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                            MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double frequency = 0.;
        double wordCount = 0.;
        for (String tweet : tweets) {
            List<String> words = tweetParser.parse(tweet);
            for (String word : words) {
                frequency += frequencyAnalyzer.getFrequency(word);
            }
            wordCount += words.size();
        }
        attributes.add(getAttribute(frequency / wordCount));
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, "frequency_per_word");
    }
}
