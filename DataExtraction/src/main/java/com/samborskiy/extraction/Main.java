package com.samborskiy.extraction;

import com.samborskiy.entity.Configuration;
import com.samborskiy.extraction.requests.FindUsersRequest;
import com.samborskiy.extraction.requests.TweetsRequest;
import com.samborskiy.extraction.requests.UserRequest;
import com.samborskiy.extraction.utils.DatabaseHelper;
import com.samborskiy.extraction.utils.TwitterHelper;
import twitter4j.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static Random random = new Random();

    private static List<String> getNames(String fileName) {
        try (BufferedReader bf = new BufferedReader(new FileReader(fileName))) {
            List<String> list = new ArrayList<>();
            String str;
            while ((str = bf.readLine()) != null) {
                list.add(str);
            }
            bf.close();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("res/ru/config_test_2.json");
        Configuration configuration = Configuration.build(file);
        TwitterHelper twitterHelper = new TwitterHelper(configuration);

        List<String> names = getNames(configuration.getManNames());
        names.addAll(getNames(configuration.getWomanNames()));

        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
            dbHelper.createTable();

            for (String screenName : configuration.getCorporateTwitterAccounts()) {
                User user = new UserRequest(twitterHelper, screenName, true).make();
                String tweets = new TweetsRequest(twitterHelper, user, configuration.getCorporateTweetPerUser()).make();
                dbHelper.insert(user.getId(), user.getScreenName(), tweets, 1);
                System.out.println("Get information about corporate: " + user.getScreenName());
            }

            int personalAccountsNumber = 0;
            while (personalAccountsNumber < configuration.getNumberOfPersonalAccounts() && !names.isEmpty()) {
                String name = names.remove(random.nextInt(names.size()));
                List<User> users = new FindUsersRequest(twitterHelper, name).make();
                System.out.format("Get users with name: %s (%d)\n", name, users.size());
                for (User user : users) {
                    String tweets = new TweetsRequest(twitterHelper, user, configuration.getPersonalTweetPerUser()).make();
                    dbHelper.insert(user.getId(), user.getScreenName(), tweets, 0);
                    System.out.println("Get information about person: " + user.getScreenName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
