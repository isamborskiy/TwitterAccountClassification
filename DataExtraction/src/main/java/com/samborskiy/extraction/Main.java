// CHECKSTYLE:OFF
package com.samborskiy.extraction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.samborskiy.extraction.utils.TwitterHelper;
import twitter4j.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final long API_RESTART = TimeUnit.MINUTES.toMillis(15);
    private static final int MAX_API = 180;
    private static int apiCount = 0;
    private static Random random = new Random();
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    private static void incCounter() {
        if (apiCount >= MAX_API) {
            try {
                System.out.println("SLEEP");
                Thread.currentThread().sleep(API_RESTART);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        apiCount %= MAX_API;
        apiCount++;
    }

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
        File file = new File("DataExtraction/res/ru/config.json");
        Configuration configuration = Configuration.build(file);
        TwitterHelper twitterHelper = new TwitterHelper(configuration);

        List<String> names = getNames(configuration.getManNames());
        names.addAll(getNames(configuration.getWomanNames()));

        int index = names.indexOf("соня");
        System.out.println("соня".equals(names.get(335)));
        String name = names.remove(index);
        List<User> users = twitterHelper.findUsersByName(name);
        System.out.println("Name: " + name + "(" + users.size() + ")" + " ---- " + apiCount);
        System.out.println(users.toString());

//        try (DatabaseHelper dbHelper = new DatabaseHelper(configuration)) {
//            dbHelper.createTable();
//
//            for (String screenName : configuration.getCorporateTwitterAccounts()) {
//                incCounter();
//                User user = twitterHelper.getUser(screenName, true);
//                String tweets = twitterHelper.getTweets(user.getScreenName(), configuration.getCorporateTweetPerUser());
//                dbHelper.insert(user.getId(), user.getScreenName(), tweets, 1);
//                System.out.println(user.getScreenName() + " " + apiCount);
//            }
//
//            int personalAccountsNumber = 0;
//            while (personalAccountsNumber < configuration.getNumberOfPersonalAccounts()
//                    && !names.isEmpty()) {
//                apiCount += 4;
//                incCounter();
//                String name = names.remove(random.nextInt(names.size()));
//                List<User> users = twitterHelper.findUsersByName(name);
//                System.out.println("Name: " + name + "(" + users.size() + ")" + " ---- " + apiCount);
//                for (User user : users) {
//                    incCounter();
//                    String tweets = twitterHelper.getTweets(user.getScreenName(), configuration.getCorporateTweetPerUser());
//                    dbHelper.insert(user.getId(), user.getScreenName(), tweets, 0);
//                    System.out.println(user.getScreenName() + " " + apiCount);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
