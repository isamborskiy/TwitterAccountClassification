package com.samborskiy.test.attrs;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.attributes.Attribute;
import com.samborskiy.entity.instances.attributes.AvrSignsNumber;
import com.samborskiy.entity.instances.attributes.SignLatitude;
import com.samborskiy.entity.instances.attributes.SignPerTweet;
import com.samborskiy.entity.instances.attributes.SignsPerTweet;
import com.samborskiy.entity.instances.attributes.TweetsWithSign;
import com.samborskiy.entity.instances.attributes.TweetsWithSigns;
import com.samborskiy.entity.instances.attrs.Account;
import com.samborskiy.test.attrs.testmachines.KNNTestMachine;
import com.samborskiy.test.attrs.testmachines.TestMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 23.04.2015.
 */
public class KNNTest extends Test {

    public KNNTest(Configuration configuration, int rounds, int folds) {
        super(configuration, rounds, folds);
    }

    @Override
    protected List<Attribute> getAttributes() {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new AvrSignsNumber());
        attributes.add(new SignLatitude());
        attributes.add(new SignPerTweet());
        attributes.add(new SignsPerTweet());
        attributes.add(new TweetsWithSign());
        attributes.add(new TweetsWithSigns());
        return attributes;
    }

    @Override
    protected TestMachine getTestMachine(List<Account> accounts, boolean isParallel) {
        return new KNNTestMachine(accounts, isParallel);
    }
}
