package com.samborskiy.extraction;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.utils.DatabaseHelper;
import com.samborskiy.entity.utils.TwitterHelper;
import com.samborskiy.extraction.requests.RetweetsRequest;
import twitter4j.TwitterException;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 14.05.2015.
 */
public class GetRetweets {

    public static void main(String[] args) throws TwitterException {
        File file = new File("res/ru/config.json");
        Configuration configuration = Configuration.build(file);
        TwitterHelper twitterHelper = new TwitterHelper(configuration);

        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
            ResultSet set = dbHelper.getAll();
            List<String> screenNames = new ArrayList<>();
            while (!set.isAfterLast()) {
                String screenName = set.getString(DatabaseHelper.SCREEN_NAME);
                set.getInt(DatabaseHelper.RETWEETS);
                if (set.wasNull()) {
                    screenNames.add(screenName);
                }
                set.next();
            }
            set.close();
            for (String screenName : screenNames) {
                System.out.format("Start update user: %s\n", screenName);
                float retweets = new RetweetsRequest(twitterHelper, screenName, 500).make();
                dbHelper.addRetweet(screenName, retweets);
                System.out.format("Update user: %s\n", screenName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
