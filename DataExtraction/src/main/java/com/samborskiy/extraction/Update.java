package com.samborskiy.extraction;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Type;
import com.samborskiy.entity.utils.DatabaseHelper;
import com.samborskiy.entity.utils.TwitterHelper;
import com.samborskiy.extraction.requests.FindUsersRequest;
import com.samborskiy.extraction.requests.TweetsRequest;
import com.samborskiy.extraction.requests.UserRequest;
import twitter4j.User;

import java.io.File;
import java.util.List;

public class Update {

    public static void main(String[] args) throws Exception {
        File file = new File("res/en/config.json");
        Configuration configuration = Configuration.build(file);
        TwitterHelper twitterHelper = new TwitterHelper(configuration);

        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
//            if (!new File(configuration.getDatabasePath()).exists()) {
//                dbHelper.createTable();
//            }
            for (Type type : configuration) {
                for (String screenName : type.getData().extractScreenNames()) {
                    if (!dbHelper.hasUser(screenName)) {
                        User user = new UserRequest(twitterHelper, screenName, type.hasConstraints()).make();
                        tweetsExtraction(twitterHelper, dbHelper, type, user);
                    }
                }
                for (String name : type.getData().extractPeopleNames()) {
                    List<User> users = new FindUsersRequest(twitterHelper, name).make();
                    for (User user : users) {
                        if (!dbHelper.hasUser(user.getScreenName())) {
                            tweetsExtraction(twitterHelper, dbHelper, type, user);
                        }
                    }
                }
            }
            dbHelper.deleteEmptyTweetsRow();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void tweetsExtraction(TwitterHelper twitterHelper, DatabaseHelper dbHelper, Type type, User user) throws InterruptedException {
        String tweets = new TweetsRequest(twitterHelper, user, type.getTweetPerUser()).make();
        dbHelper.insert(user.getId(), user, tweets, type.getId());
        System.out.format("Get information about %s: %s\n", type.getName(), user.getScreenName());
    }
}
