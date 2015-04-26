package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.functions.AttributeFunction;
import com.samborskiy.test.KNNTest;
import com.samborskiy.test.Statistics;
import com.samborskiy.test.Test;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        Test test = new KNNTest(configuration);
        test.test("test_file", FOLD_COUNT, getAttributes());
        System.out.print(new Statistics(test));
    }

    private static List<AttributeFunction> getAttributes() throws InstantiationException, IllegalAccessException {
        List<AttributeFunction> attributeFunctions = new ArrayList<>();
        attributeFunctions.addAll(getAttributes("com.samborskiy.entity.instances.functions.partofspeech"));
        attributeFunctions.addAll(getAttributes("com.samborskiy.entity.instances.functions.sign"));
        return attributeFunctions;
    }

    private static List<AttributeFunction> getAttributes(String packageName) throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends AttributeFunction>> allClasses = reflections.getSubTypesOf(AttributeFunction.class);
        List<AttributeFunction> attributeFunctions = new ArrayList<>();
        for (Class clazz : allClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                attributeFunctions.add((AttributeFunction) clazz.newInstance());
            }
        }
        return attributeFunctions;
    }
}
