package com.samborskiy.word.generator;

/**
 * Created by Whiplash on 10.05.2015.
 */
public class Main {

    private static final String FILE_NAME = "fssTable.docx";

    public static void main(String[] args) throws Exception {
        new FSSTableGenerator(FILE_NAME).generate();
    }
}
