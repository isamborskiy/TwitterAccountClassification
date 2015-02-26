package com.samborskiy.extraction;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Type;
import com.samborskiy.extraction.requests.FindUsersRequest;
import com.samborskiy.extraction.requests.TweetsRequest;
import com.samborskiy.extraction.requests.UserRequest;
import com.samborskiy.extraction.utils.DatabaseHelper;
import com.samborskiy.extraction.utils.TwitterHelper;
import twitter4j.User;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        File file = new File("res/ru/test_config.json");
        Configuration configuration = Configuration.build(file);
        TwitterHelper twitterHelper = new TwitterHelper(configuration);

        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
            dbHelper.createTable();
            for (Type type : configuration) {
                for (String screenName : type.getData().extractScreenNames()) {
                    User user = new UserRequest(twitterHelper, screenName, type.hasConstraints()).make();
                    String tweets = new TweetsRequest(twitterHelper, user, type.getTweetPerUser()).make();
                    dbHelper.insert(user.getId(), user.getScreenName(), tweets, type.getId());
                    System.out.format("Get information about %s: %s\n", type.getName(), user.getScreenName());
                }

                for (String name : type.getData().extractPeopleNames()) {
                    List<User> users = new FindUsersRequest(twitterHelper, name).make();
                    for (User user : users) {
                        String tweets = new TweetsRequest(twitterHelper, user, type.getTweetPerUser()).make();
                        dbHelper.insert(user.getId(), user.getScreenName(), tweets, 0);
                        System.out.format("Get information about %s: %s\n", type.getName(), user.getScreenName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
