package com.samborskiy.entity.instances;

import com.samborskiy.entity.Language;

import java.util.function.BiFunction;

public interface WordModifier extends BiFunction<String, Language, String> {
    // rename interface...
}
