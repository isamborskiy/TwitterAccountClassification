package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.test.KNNTest;
import com.samborskiy.test.Statistics;

import java.io.File;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        KNNTest test = new KNNTest(configuration);
        test.test("test_file", FOLD_COUNT);
        System.out.print(new Statistics(test));
    }
}
