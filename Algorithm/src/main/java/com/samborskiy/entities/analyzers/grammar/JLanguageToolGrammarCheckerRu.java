package com.samborskiy.entities.analyzers.grammar;

import org.languagetool.JLanguageTool;
import org.languagetool.language.Russian;

import java.io.IOException;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class JLanguageToolGrammarCheckerRu extends GrammarAnalyzer {

    protected final JLanguageTool languageTool;

    public JLanguageToolGrammarCheckerRu() {
        this.languageTool = new JLanguageTool(new Russian());
    }

    @Override
    public int check(String text) throws IOException {
        return languageTool.check(text).size();
    }
}
