package com.samborskiy.entity.analyzers.grammar;

import org.languagetool.JLanguageTool;
import org.languagetool.language.English;

import java.io.IOException;

/**
 * Created by Whiplash on 28.04.2015.
 */
public class JLanguageToolGrammarCheckerEn extends GrammarAnalyzer {

    protected final JLanguageTool languageTool;

    public JLanguageToolGrammarCheckerEn() {
        this.languageTool = new JLanguageTool(new English());
    }

    @Override
    public int check(String text) throws IOException {
        return languageTool.check(text).size();
    }
}
