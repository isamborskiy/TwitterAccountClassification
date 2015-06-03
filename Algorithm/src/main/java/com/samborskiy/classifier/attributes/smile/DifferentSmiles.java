package com.samborskiy.classifier.attributes.smile;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class DifferentSmiles extends SmileFunction {

    public DifferentSmiles(FrequencyAnalyzer frequencyAnalyzer, GrammarAnalyzer grammarAnalyzer,
                           MorphologicalAnalyzer morphologicalAnalyzer, TweetParser tweetParser) {
        super(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser);
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0;
        for (SmileSequence smile : SMILES) {
            for (String tweet : tweets) {
                if (hasSmile(smile, tweet)) {
                    count++;
                    break;
                }
            }
        }
        attributes.add(getAttribute(count / SMILES.size()));
    }

    private boolean hasSmile(SmileSequence smile, String tweet) {
        for (int i = 0; i < tweet.length(); i++) {
            if (smile.match(tweet, i)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, String.format("different_smiles", args));
    }
}
