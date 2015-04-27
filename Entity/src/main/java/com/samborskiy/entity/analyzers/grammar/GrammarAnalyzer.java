package com.samborskiy.entity.analyzers.grammar;

import java.io.IOException;

/**
 * Created by Whiplash on 28.04.2015.
 */
public interface GrammarAnalyzer {

    public int check(String text) throws IOException;

    public static GrammarAnalyzer get() {
        return new JLanguageToolGrammarChecker();
    }
}
