package com.samborskiy.word.generator;

/**
 * Created by Whiplash on 10.05.2015.
 */
public abstract class WordGenerator {

    protected final String fileName;

    public WordGenerator(String fileName) {
        this.fileName = fileName;
    }

    public abstract void generate() throws Exception;

    protected String getClassifierName(String fullName) {
        return fullName.substring(0, fullName.indexOf(" "));
    }

    protected String getClassifierParams(String fullName) {
        return fullName.substring(fullName.indexOf(" ") + 1);
    }
}
