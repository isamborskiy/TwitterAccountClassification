// CHECKSTYLE:OFF
package com.samborskiy.extraction;

import com.samborskiy.extraction.utils.DatabaseHelper;
import com.samborskiy.extraction.utils.TwitterHelper;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws TwitterException {
        File file = new File("DataExtraction/res/ru/config.json");
        Configuration configuration = Configuration.build(file);

        TwitterHelper twitterHelper = new TwitterHelper(configuration);
        List<User> users = new ArrayList<>();
        List<String> usersScreenName = new ArrayList<>();
        List<String> tweets = new ArrayList<>();
        users.add(twitterHelper.getUser(configuration.getInitPersonalScreenName()));
        users.addAll(twitterHelper.getFollowers(users.get(0)));
        for (User user : users) {
            System.out.println(user.getScreenName());
            usersScreenName.add(user.getScreenName());
            tweets.add(twitterHelper.getTweets(user.getScreenName()));
        }

        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
            dbHelper.createTable();
            dbHelper.insertAll(usersScreenName, tweets, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
