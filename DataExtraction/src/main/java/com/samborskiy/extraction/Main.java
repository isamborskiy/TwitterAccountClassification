// CHECKSTYLE:OFF
package com.samborskiy.extraction;

import com.samborskiy.extraction.utils.DatabaseHelper;
import com.samborskiy.extraction.utils.TwitterHelper;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final long API_RESTART = TimeUnit.MINUTES.toMillis(15);
    private static final int MAX_API = 180;
    private static int apiCount = 0;

    private static void incCounter() {
        if (apiCount == MAX_API) {
            try {
                Thread.currentThread().sleep(API_RESTART);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        apiCount++;
        apiCount %= MAX_API;
    }

    public static void main(String[] args) throws TwitterException, URISyntaxException, MalformedURLException, UnsupportedEncodingException {
        File file = new File("DataExtraction/res/ru/config.json");
        Configuration configuration = Configuration.build(file);
        TwitterHelper twitterHelper = new TwitterHelper(configuration);

        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
            dbHelper.createTable();

            for (String account : configuration.getCorporateTwitterAccounts()) {
                incCounter();
                User user = twitterHelper.getUser(account, true);
                String tweets = twitterHelper.getTweets(user.getScreenName());
                dbHelper.insert(user.getId(), user.getScreenName(), tweets, 0);
                System.out.println(apiCount);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
