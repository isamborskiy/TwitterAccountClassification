package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.functions.AttributeFunction;
import com.samborskiy.entity.instances.functions.AvrSignsNumber;
import com.samborskiy.entity.instances.functions.SignLatitude;
import com.samborskiy.entity.instances.functions.SignPerTweet;
import com.samborskiy.entity.instances.functions.SignsPerTweet;
import com.samborskiy.entity.instances.functions.TweetsWithSign;
import com.samborskiy.entity.instances.functions.TweetsWithSigns;
import com.samborskiy.weka.DatabaseToArff;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;
    private static final int ROUNDS = 1;

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        DatabaseToArff.write(configuration, "test_file", getAttributes());

        BufferedReader datafile = new BufferedReader(new FileReader("test_file.arff"));
        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);

        //do not use first and second
        Instance first = data.instance(0);
        Instance second = data.instance(1);
        data.delete(0);
        data.delete(1);

        Classifier ibk = new IBk();
        ibk.buildClassifier(data);

        double class1 = ibk.classifyInstance(first);
        double class2 = ibk.classifyInstance(second);

        System.out.println("first: " + class1 + "\nsecond: " + class2);
    }

    private static List<AttributeFunction> getAttributes() {
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
