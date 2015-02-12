package com.samborskiy.misc;

import com.samborskiy.extraction.utils.DatabaseHelper;
import com.samborskiy.extraction.utils.EntityUtil;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 12.02.2015.
 */
public class DatabaseToArff {

    public static final String PERSONAL = "personal";
    public static final String CORPORATE = "corporate";

    public static void create(DatabaseHelper databaseHelper, String fileName) {
        ArrayList<Attribute> features = getFeatures();
        List<Instance> instances = getInstances(databaseHelper, features);
        Instances trainingSet = new Instances("Training set", features, instances.size());
        trainingSet.setClassIndex(1);
        trainingSet.addAll(instances);
        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.println(trainingSet.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Attribute> getFeatures() {
        Attribute message = new Attribute("message", (FastVector) null, 0);
        List<String> featureClass = new ArrayList<>(2);
        featureClass.add(PERSONAL);
        featureClass.add(CORPORATE);
        Attribute classAttribute = new Attribute("theClass", featureClass, 1);
        ArrayList<Attribute> features = new ArrayList<>(2);
        features.add(message);
        features.add(classAttribute);
        return features;
    }

    private static List<Instance> getInstances(DatabaseHelper databaseHelper, List<Attribute> features) {
        try {
            List<Instance> instances = new ArrayList<>();
            ResultSet table = databaseHelper.getAll();
            while (table.next()) {
                instances.addAll(parseRow(table, features));
            }
            table.close();
            return instances;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Instance> parseRow(ResultSet row, List<Attribute> features) throws SQLException, IOException {
        List<Instance> instances = new ArrayList<>();
        String[] tweets = EntityUtil.deserialize(row.getString(DatabaseHelper.TWEETS).getBytes(), String[].class);
        String type = getType(row.getInt(DatabaseHelper.ACCOUNT_TYPE));
        for (String tweet : tweets) {
            Instance instance = new DenseInstance(2);
//            features.get(0).addStringValue(tweet);
            instance.setValue(features.get(0), tweet);
            instance.setValue(features.get(1), type);
            instances.add(instance);
        }
        return instances;
    }

    private static String getType(int typeNumber) {
        if (typeNumber == 0) {
            return PERSONAL;
        } else {
            return CORPORATE;
        }
    }

}