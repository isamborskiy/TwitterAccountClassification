package com.samborskiy.word.generator;

import com.samborskiy.InformationGain;
import com.samborskiy.feature.extraction.PCA;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Whiplash on 10.05.2015.
 */
public class Translate {

    private static final String FILE_NAME = "translate.docx";

    public static void main(String[] args) throws Exception {
        BufferedReader datafile = new BufferedReader(new FileReader("train.arff"));
        Instances instances = new Instances(datafile);
        instances.setClassIndex(instances.numAttributes() - 1);

        Instances selectedInstances = new PCA().select(instances);

        new TranslateAttributes(FILE_NAME, selectedInstances).generate();

        /*Set<Map.Entry<String, Double>> sortedStatistics = InformationGain.getStatistics();
        List<Map.Entry<String, Double>> reverseStatistics = new ArrayList<>();
        reverseStatistics.addAll(sortedStatistics);
        Collections.reverse(reverseStatistics);
        TranslateAttributes translator = new TranslateAttributes("", null);
        for (Map.Entry<String, Double> statistic : reverseStatistics) {
//            System.out.format("%s is %.3f\n", translator.translate(statistic.getKey()), statistic.getValue());
//            System.out.format("%s\n", translator.translate(statistic.getKey()));
            System.out.format("%.3f\n", statistic.getValue());
        }*/
    }
}
