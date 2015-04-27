package com.samborskiy.entity.analyzers.grammar;

import org.languagetool.JLanguageTool;
import org.languagetool.language.Russian;

import java.io.IOException;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class JLanguageToolGrammarChecker implements GrammarAnalyzer {

    protected final JLanguageTool languageTool;

    public JLanguageToolGrammarChecker() {
        this.languageTool = new JLanguageTool(new Russian());
    }

    @Override
    public int check(String text) throws IOException {
        return languageTool.check(text).size();
    }
}
