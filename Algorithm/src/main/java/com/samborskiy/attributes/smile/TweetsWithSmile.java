package com.samborskiy.attributes.smile;

import com.samborskiy.entities.sequences.SmileSequence;
import com.samborskiy.entities.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class TweetsWithSmile extends SmileFunction {

    private final SmileSequence smile;

    public TweetsWithSmile(SmileSequence smile) {
        this.smile = smile;
    }

    @Override
    public String getName() {
        return String.format("tweets_with_%s", smile.toString());
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0;
        for (String tweet : tweets) {
            count += smile.contains(tweet) ? 1 : 0;
        }
        attributes.add(new Attribute(count / tweets.size(), getName()));
    }
}
