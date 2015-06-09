package com.samborskiy.classifier.attributes.meta;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.Account;
import com.samborskiy.entity.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i.samborskiy on 09.06.2015.
 */
public class FollowingNumber extends AttributeFunction {

    @Override
    public String getName() {
        return "following_number";
    }

    @Override
    public List<Attribute> apply(Account account) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute(account.getFollowing(), getName()));
        return attributes;
    }

    @Override
    protected void apply(List<Attribute> attributes, List<String> tweets) {
        // empty ...
    }
}
