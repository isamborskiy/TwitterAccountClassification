package com.samborskiy.entity.analyzers.grammar;

import java.io.IOException;

/**
 * Created by Whiplash on 28.04.2015.
 */
public abstract class GrammarAnalyzer {

    private static final GrammarAnalyzer analyzer = new JLanguageToolGrammarChecker();

    public abstract int check(String text) throws IOException;

    public static GrammarAnalyzer get() {
        return analyzer;
    }
}
