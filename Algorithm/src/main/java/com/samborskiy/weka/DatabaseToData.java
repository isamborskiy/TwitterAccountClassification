package com.samborskiy.weka;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.utils.EntityUtil;
import com.samborskiy.extraction.utils.DatabaseHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Convert data from database to list of instance.
 *
 * @author Whiplash
 */
class DatabaseToData {

    private DatabaseToData() {
    }

    /**
     * Returns list of user {@link com.samborskiy.entity.instances.Account} get from database.
     *
     * @param configuration to get access to database describe in configuration
     * @return list of user {@link com.samborskiy.entity.instances.Account} get from database
     */
    public static List<Account> getAllAccounts(Configuration configuration) {
        List<Account> instances = new ArrayList<>();
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(configuration);
            ResultSet cursor = databaseHelper.getAll();
            while (cursor.next()) {
                Account account = new Account(cursor.getInt(DatabaseHelper.ACCOUNT_TYPE));
                account.addTweets(parseRow(cursor));
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
     * @param cursor cursor on table row
     * @return list of parsed tweets
     * @throws java.sql.SQLException trouble with database connect
     * @throws java.io.IOException   incorrect tweets json
     */
    private static List<String> parseRow(ResultSet cursor) throws SQLException, IOException {
        String[] tweets = EntityUtil.deserialize(cursor.getString(DatabaseHelper.TWEETS).getBytes(), String[].class);
        return Arrays.asList(tweets);
    }

}
