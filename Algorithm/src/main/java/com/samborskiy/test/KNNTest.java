package com.samborskiy.test;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.functions.AttributeFunction;
import com.samborskiy.entity.instances.functions.AvrSignsNumber;
import com.samborskiy.entity.instances.functions.SignLatitude;
import com.samborskiy.entity.instances.functions.SignPerTweet;
import com.samborskiy.entity.instances.functions.SignsPerTweet;
import com.samborskiy.entity.instances.functions.TweetsWithSign;
import com.samborskiy.entity.instances.functions.TweetsWithSigns;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 24.04.2015.
 */
public class KNNTest extends Test {

    public KNNTest(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected List<AttributeFunction> getAttributes() {
        List<AttributeFunction> attributeFunctions = new ArrayList<>();
        attributeFunctions.add(new AvrSignsNumber());
        attributeFunctions.add(new SignLatitude());
        attributeFunctions.add(new SignPerTweet());
        attributeFunctions.add(new SignsPerTweet());
        attributeFunctions.add(new TweetsWithSign());
        attributeFunctions.add(new TweetsWithSigns());
        return attributeFunctions;
    }

    @Override
    protected Classifier getClassifier() {
        return new IBk();
    }
}
