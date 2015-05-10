package com.samborskiy;

import com.samborskiy.classifiers.ClassifierWrapper;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.functions.AccountFunction;
import com.samborskiy.feature.Feature;
import com.samborskiy.feature.NoFeatureSelection;
import com.samborskiy.statistic.Statistic;
import com.samborskiy.statistic.Test;
import com.samborskiy.statistic.WekaTest;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.reflections.Reflections;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Main {

    private static final String TRAIN_FILE_PATH = "res/ru/config.json";
    private static final int FOLD_COUNT = 5;
    private static final String RELATION_NAME = "train";

    public static void main(String[] args) throws Exception {
        File configFileTrain = new File(TRAIN_FILE_PATH);
        Configuration configuration = Configuration.build(configFileTrain);
        Test test = new WekaTest(configuration, RELATION_NAME, getClassifierWrappers(), getTweetAttributes(), getFeatures());
        List<Statistic> statistics = test.test(FOLD_COUNT, true);
        Collections.sort(statistics);
        statistics.forEach(System.out::println);
    }

    private static void writeWordFile(List<Statistic> statistics, int classifierNumber) throws IOException {
        int fss = statistics.size() / classifierNumber;
        int rows = fss * (classifierNumber + 1) + 1;
        XWPFDocument doc = new XWPFDocument();
        XWPFTable table = doc.createTable(rows, 3);

        int featureNameRow = 1;
        int firstAlgorithmRow = 2;
        for (int i = 0; i < fss; i++) {
            int firstStatisticIndex = i * classifierNumber;
            table.getRow(featureNameRow).getCell(0).setText(statistics.get(firstStatisticIndex).getFeatureSelectionName());
            for (int j = 0; j < classifierNumber; j++) {
                Statistic statistic = statistics.get(firstStatisticIndex + j);
                XWPFTableRow row = table.getRow(firstAlgorithmRow + j);
                row.getCell(0).setText(statistic.getClassifierName());
                row.getCell(1).setText(statistic.getAccuracyString());
                row.getCell(2).setText(statistic.getFMeasureString());
            }
            featureNameRow += classifierNumber + 1;
            firstAlgorithmRow += classifierNumber + 1;
        }

        try (FileOutputStream out = new FileOutputStream("simpleTable.docx")) {
            doc.write(out);
        }
    }

    private static List<ClassifierWrapper> getClassifierWrappers() throws Exception {
        List<ClassifierWrapper> wrappers = new ArrayList<>();
//        wrappers.addAll(new RandomForestVariation().getClassifiers());

        wrappers.add(new ClassifierWrapper(new IBk()));
        wrappers.add(new ClassifierWrapper(new NaiveBayes()));
        wrappers.add(new ClassifierWrapper(new LibSVM()));
        wrappers.add(new ClassifierWrapper(new J48()));
        wrappers.add(new ClassifierWrapper(new RandomForest()));
        return wrappers;
    }

    private static List<Feature> getFeatures() throws InstantiationException, IllegalAccessException {
        List<Feature> featureSelections = new ArrayList<>();
        featureSelections.add(new NoFeatureSelection());
//        featureSelections.addAll(getFeatures("com.samborskiy.feature.selection"));
//        featureSelections.addAll(getFeatures("com.samborskiy.feature.extraction"));
        return featureSelections;
    }

    private static List<Feature> getFeatures(String packageName) throws IllegalAccessException, InstantiationException {
        return getClasses(packageName, Feature.class);
    }

    private static List<AccountFunction> getTweetAttributes() throws InstantiationException, IllegalAccessException {
        List<AccountFunction> accountFunctions = new ArrayList<>();
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.partofspeech"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.sign"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.smile"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.length"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.grammar"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.vocabulary"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.hashtag"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.reference"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.personal"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.entity.functions.frequency"));
        return accountFunctions;
    }

    private static List<AccountFunction> getTweetAttributes(String packageName) throws IllegalAccessException, InstantiationException {
        return getClasses(packageName, AccountFunction.class);
    }

    private static <E> List<E> getClasses(String packageName, Class<E> type) throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends E>> allClasses = reflections.getSubTypesOf(type);
        List<E> classes = new ArrayList<>();
        for (Class clazz : allClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                classes.add((E) clazz.newInstance());
            }
        }
        return classes;
    }
}
