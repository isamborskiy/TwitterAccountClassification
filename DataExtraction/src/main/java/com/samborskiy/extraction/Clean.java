package com.samborskiy.extraction;

import com.samborskiy.entity.Configuration;
import com.samborskiy.extraction.utils.DatabaseHelper;

import java.io.File;

/**
 * Created by Whiplash on 18.04.2015.
 */
public class Clean {

    public static void main(String[] args) {
        File file = new File("res/ru/config.json");
        Configuration configuration = Configuration.build(file);
        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
            dbHelper.deleteEmptyTweetsRow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
