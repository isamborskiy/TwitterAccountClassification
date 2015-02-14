package com.samborskiy.misc;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;
import com.samborskiy.entity.utils.EntityUtil;
import com.samborskiy.extraction.utils.DatabaseHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 14.02.2015.
 */
public class InstancesFromDatabase {

    private InstancesFromDatabase() {
    }

    public static List<Tweet> get(Configuration configuration) {
        List<Tweet> instances = new ArrayList<>();
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(configuration);
            ResultSet cursor = databaseHelper.getAll();
            while (cursor.next()) {
                instances.addAll(parseRow(cursor, configuration));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instances;
    }

    private static List<Tweet> parseRow(ResultSet row, Configuration configuration) throws SQLException, IOException {
        List<Tweet> instances = new ArrayList<>();
        String[] tweets = EntityUtil.deserialize(row.getString(DatabaseHelper.TWEETS).getBytes(), String[].class);
        for (String tweet : tweets) {
            instances.add(new Tweet(tweet, row.getInt(DatabaseHelper.ACCOUNT_TYPE), configuration));
        }
        return instances;
    }


}
