// CHECKSTYLE:OFF
package com.samborskiy;

import com.samborskiy.extraction.Configuration;
import com.samborskiy.extraction.utils.DatabaseHelper;
import com.samborskiy.misc.DatabaseToArff;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File file = new File("DataExtraction/res/ru/config.json");
        Configuration configuration = Configuration.build(file);
        try (DatabaseHelper databaseHelper = new DatabaseHelper(configuration)) {
            DatabaseToArff.create(databaseHelper, "test.arff");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
