package com.samborskiy.entity.functions.smile;

import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class DifferentSmiles extends SmileFunction {

    @Override
    public List<Attribute> apply(List<String> tweets) {
        List<Attribute> attrs = new ArrayList<>();
        double count = 0;
        for (SmileSequence smile : SMILES) {
            for (String tweet : tweets) {
                if (hasSmile(smile, tweet)) {
                    count++;
                    break;
                }
            }
        }
        attrs.add(getAttribute(count / SMILES.size()));
        return attrs;
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
