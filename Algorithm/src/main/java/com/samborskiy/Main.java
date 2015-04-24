package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.functions.AttributeFunction;
import com.samborskiy.entity.instances.functions.AvrSignsNumber;
import com.samborskiy.entity.instances.functions.SignLatitude;
import com.samborskiy.entity.instances.functions.SignPerTweet;
import com.samborskiy.entity.instances.functions.SignsPerTweet;
import com.samborskiy.entity.instances.functions.TweetsWithSign;
import com.samborskiy.entity.instances.functions.TweetsWithSigns;
import com.samborskiy.test.NaiveBayesTest;
import com.samborskiy.test.Statistics;
import com.samborskiy.test.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        Test test = new NaiveBayesTest(configuration);
        test.test("test_file", FOLD_COUNT, getPunctuationAttributes());
        System.out.print(new Statistics(test));
    }

    private static List<AttributeFunction> getPunctuationAttributes() {
        List<AttributeFunction> attributeFunctions = new ArrayList<>();
        attributeFunctions.add(new AvrSignsNumber());
        attributeFunctions.add(new SignLatitude());
        attributeFunctions.add(new SignPerTweet());
        attributeFunctions.add(new SignsPerTweet());
        attributeFunctions.add(new TweetsWithSign());
        attributeFunctions.add(new TweetsWithSigns());
        return attributeFunctions;
    }
}
