package com.samborskiy;

import com.samborskiy.entities.Account;
import com.samborskiy.extraction.entity.Configuration;
import com.samborskiy.extraction.utils.DatabaseHelper;
import com.samborskiy.extraction.utils.EntityUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Whiplash on 10.06.2015.
 */
public class Main {

    public static void main(String[] args) {

    }

    /**
     * Returns list of user {@link com.samborskiy.entities.Account} get from database.
     *
     * @param configuration to get access to database describe in configuration
     * @return list of user {@link com.samborskiy.entities.Account} get from database
     */
    public static List<Account> getAllAccounts(Configuration configuration) {
        List<Account> instances = new ArrayList<>();
        try (DatabaseHelper databaseHelper = new DatabaseHelper(configuration)) {
            ResultSet cursor = databaseHelper.getAll();
            while (cursor.next()) {
                Account account = new Account(cursor.getInt(DatabaseHelper.ACCOUNT_TYPE),
                        cursor.getInt(DatabaseHelper.FOLLOWERS),
                        cursor.getInt(DatabaseHelper.FOLLOWING),
                        cursor.getInt(DatabaseHelper.VERIFIED),
                        cursor.getInt(DatabaseHelper.VERIFIED));
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
