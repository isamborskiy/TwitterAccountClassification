package com.samborskiy.extraction;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.utils.DatabaseHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Whiplash on 14.05.2015.
 */
public class Delete {

    public static void main(String[] args) throws Exception {
        File file = new File("res/ru/config.json");
        Configuration configuration = Configuration.build(file);

        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
            try (BufferedReader bf = new BufferedReader(new FileReader("delete_file"))) {
                String screenName;
                while ((screenName = bf.readLine()) != null) {
                    dbHelper.deleteByScreenName(screenName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
