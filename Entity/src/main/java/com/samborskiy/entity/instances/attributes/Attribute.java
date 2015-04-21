package com.samborskiy.entity.instances.attributes;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Whiplash on 22.04.2015.
 */
public interface Attribute extends Function<List<String>, List<Double>> {
    // rename Function<List, Double> to Attribute...
}
