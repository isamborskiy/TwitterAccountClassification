package com.samborskiy.word.generator;

import com.samborskiy.feature.selection.CFS_GS;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Whiplash on 10.05.2015.
 */
public class Translate {

    private static final String FILE_NAME = "translate.docx";

    public static void main(String[] args) throws Exception {
        BufferedReader datafile = new BufferedReader(new FileReader("train.arff"));
        Instances instances = new Instances(datafile);
        instances.setClassIndex(instances.numAttributes() - 1);

        Instances selectedInstances = new CFS_GS().select(instances);

        new TranslateAttributes(FILE_NAME, selectedInstances).generate();
    }
}
