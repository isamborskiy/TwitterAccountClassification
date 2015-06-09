package com.samborskiy.entities.analyzers.grammar;

import java.io.IOException;

/**
 * Created by Whiplash on 28.04.2015.
 */
public abstract class GrammarAnalyzer {

    private static final GrammarAnalyzer analyzer = new JLanguageToolGrammarCheckerRu();

    public static GrammarAnalyzer get() {
        return analyzer;
    }

    public abstract int check(String text) throws IOException;
}
