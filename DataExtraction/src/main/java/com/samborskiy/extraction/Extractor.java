package com.samborskiy.extraction;

import com.samborskiy.entity.Log;
import com.samborskiy.extraction.entity.Configuration;
import com.samborskiy.extraction.entity.Type;
import com.samborskiy.extraction.requests.TweetsRequest;
import com.samborskiy.extraction.requests.UserRequest;
import com.samborskiy.extraction.utils.DatabaseHelper;
import com.samborskiy.extraction.utils.TwitterHelper;
import twitter4j.User;

import java.io.File;

public class Extractor {

    private final File configurationFile;

    public Extractor(File configurationFile) {
        this.configurationFile = configurationFile;
    }

    public void update() throws Exception {
        Configuration configuration = Configuration.build(configurationFile);
        TwitterHelper twitterHelper = new TwitterHelper(configuration);

        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
            if (!new File(configuration.getDatabasePath()).exists()) {
                dbHelper.createTable();
            }
            for (Type type : configuration) {
                for (String screenName : type.getData().extractScreenNames()) {
                    if (!dbHelper.hasUser(screenName)) {
                        User user = new UserRequest(twitterHelper, screenName).make();
                        tweetsExtraction(twitterHelper, dbHelper, type, user);
                    }
                }
                for (Long userId : type.getData().extractUserIds()) {
                    if (!dbHelper.hasUser(userId)) {
                        User user = new UserRequest(twitterHelper, userId).make();
                        tweetsExtraction(twitterHelper, dbHelper, type, user);
                    }
                }
            }
            dbHelper.deleteEmptyTweetsRow();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void tweetsExtraction(TwitterHelper twitterHelper, DatabaseHelper dbHelper, Type type, User user) throws InterruptedException {
        String tweets = new TweetsRequest(twitterHelper, user, type.getTweetPerUser()).make();
        dbHelper.insert(user.getId(), user, tweets, type.getId());
        Log.d(String.format("Get information about %s: %s", type.getName(), user.getScreenName()));
    }
}
