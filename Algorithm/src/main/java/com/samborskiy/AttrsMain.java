package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.test.attrs.KNNTest;

import java.io.File;

public class AttrsMain {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;
    private static final int ROUNDS = 1;

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        System.out.println(new KNNTest(configuration, ROUNDS, FOLD_COUNT).test(false));
    }
}
