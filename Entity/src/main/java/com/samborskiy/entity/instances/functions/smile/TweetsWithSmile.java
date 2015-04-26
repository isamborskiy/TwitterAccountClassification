package com.samborskiy.entity.instances.functions.smile;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class TweetsWithSmile extends SmileFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attrs = new ArrayList<>();
        for (SmileSequence smile : SMILES) {
            double count = 0;
            for (String tweet : tweets) {
                for (int i = 0; i < tweet.length(); i++) {
                    if (smile.match(tweet, i)) {
                        count++;
                        break;
                    }
                }
            }
            attrs.add(getAttribute(count / tweets.size(), smile.toString()));
        }
        return attrs;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, String.format("tweets_with_%s", args));
    }
}
