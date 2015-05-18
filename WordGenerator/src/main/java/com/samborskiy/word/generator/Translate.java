package com.samborskiy.word.generator;

import com.samborskiy.InformationGain;

import java.util.Map;
import java.util.Set;

/**
 * Created by Whiplash on 10.05.2015.
 */
public class Translate {

    private static final String FILE_NAME = "translate.docx";

    public static void main(String[] args) throws Exception {
//        BufferedReader datafile = new BufferedReader(new FileReader("train.arff"));
//        Instances instances = new Instances(datafile);
//        instances.setClassIndex(instances.numAttributes() - 1);
//
//        Instances selectedInstances = new CFS_GS().select(instances);
//
//        new TranslateAttributes(FILE_NAME, selectedInstances).generate();

        Set<Map.Entry<String, Double>> sortedStatistics = InformationGain.getStatistics();
        TranslateAttributes translator = new TranslateAttributes("", null);
        for (Map.Entry<String, Double> statistic : sortedStatistics) {
            System.out.format("%s is %.3f\n", translator.translate(statistic.getKey()), statistic.getValue());
        }
    }
}
