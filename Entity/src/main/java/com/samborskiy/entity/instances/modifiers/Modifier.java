package com.samborskiy.entity.instances.modifiers;

import com.samborskiy.entity.Language;

import java.util.List;
import java.util.function.BiFunction;

public interface Modifier extends BiFunction<String, Language, List<String>> {
    // rename interface...
}
