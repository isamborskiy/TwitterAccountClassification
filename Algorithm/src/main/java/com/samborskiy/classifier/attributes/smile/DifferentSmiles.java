package com.samborskiy.classifier.attributes.smile;

import com.samborskiy.classifier.entities.sequences.SmileSequence;
import com.samborskiy.entity.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 27.04.2015.
 */
public class DifferentSmiles extends SmileFunction {

    @Override
    public String getName() {
        return "different_smiles";
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        double count = 0;
        for (SmileSequence smile : SMILES) {
            for (String tweet : tweets) {
                if (smile.contains(tweet)) {
                    count++;
                    break;
                }
            }
        }
        attributes.add(new Attribute(count / SMILES.size(), getName()));
    }
}
