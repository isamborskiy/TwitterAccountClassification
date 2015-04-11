package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;

import java.util.List;
import java.util.function.BiFunction;

public interface TweetModifier extends BiFunction<String, Language, List<String>> {
    // rename interface...
}
