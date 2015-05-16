package com.samborskiy.extraction;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.utils.DatabaseHelper;

import java.io.File;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * Created by Whiplash on 18.04.2015.
 */
public class Statistics {

    public static void main(String[] args) {
        File file = new File("res/ru/config.json");
        Configuration configuration = Configuration.build(file);
        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
            ResultSet set = dbHelper.getAll();
            int[] count = new int[3];
            while (!set.isAfterLast()) {
                count[set.getInt(DatabaseHelper.ACCOUNT_TYPE)]++;
                set.next();
            }
            set.close();
            System.out.println(Arrays.toString(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
