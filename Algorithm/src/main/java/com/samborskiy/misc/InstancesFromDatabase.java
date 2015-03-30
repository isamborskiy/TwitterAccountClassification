package com.samborskiy.misc;

import com.samborskiy.entity.Account;
import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Language;
import com.samborskiy.entity.Tweet;
import com.samborskiy.entity.utils.EntityUtil;
import com.samborskiy.extraction.utils.DatabaseHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Convert data from database to list of instance.
 *
 * @author Whiplash
 */
public class InstancesFromDatabase {

    private InstancesFromDatabase() {
    }

    /**
     * Returns list of {@link Tweet} get from database.
     *
     * @param configuration to get access to database describe in configuration
     * @return list of {@link Tweet} get from database
     */
    public static List<Tweet> getAllTweets(Configuration configuration) {
        List<Tweet> instances = new ArrayList<>();
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(configuration);
            ResultSet cursor = databaseHelper.getAll();
            while (cursor.next()) {
                instances.addAll(parseRow(cursor, configuration.getLang()));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instances;
    }

    /**
     * Returns list of user {@link Tweet} get from database.
     *
     * @param configuration to get access to database describe in configuration
     * @return list of user {@link Tweet} get from database
     */
    public static List<Account> getAllAccounts(Configuration configuration) {
        List<Account> instances = new ArrayList<>();
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(configuration);
            ResultSet cursor = databaseHelper.getAll();
            while (cursor.next()) {
                Account account = new Account(cursor.getInt(DatabaseHelper.ACCOUNT_TYPE), configuration.getLang());
                account.addAll(parseRow(cursor, configuration.getLang()));
                instances.add(account);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instances;
    }

    /**
     * Returns list of parsed tweets.
     *
     * @param cursor   cursor on table row
     * @param language to generate {@link Tweet}
     * @return list of parsed tweets
     * @throws SQLException trouble with database connect
     * @throws IOException  incorrect tweets json
     */
    private static List<Tweet> parseRow(ResultSet cursor, Language language) throws SQLException, IOException {
        List<Tweet> instances = new ArrayList<>();
        String[] tweets = EntityUtil.deserialize(cursor.getString(DatabaseHelper.TWEETS).getBytes(), String[].class);
        for (String tweet : tweets) {
            instances.add(new Tweet(tweet, cursor.getInt(DatabaseHelper.ACCOUNT_TYPE), language));
        }
        return instances;
    }

}
