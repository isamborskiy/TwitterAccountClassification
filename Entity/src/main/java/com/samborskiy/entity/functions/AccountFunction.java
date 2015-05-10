package com.samborskiy.entity.functions;

import com.samborskiy.entity.instances.Attribute;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Whiplash on 22.04.2015.
 */
public abstract class AccountFunction implements Function<List<String>, List<Attribute>> {

    protected abstract Attribute getAttribute(double val, String... args);
}
