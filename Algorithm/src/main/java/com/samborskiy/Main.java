// CHECKSTYLE:OFF
package com.samborskiy;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Tweet;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File file = new File("res/ru/config.json");
        Configuration configuration = Configuration.build(file);
//        try (DatabaseHelper databaseHelper = new DatabaseHelper(configuration)) {
//            DatabaseToArff.create(databaseHelper, "test.arff");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Tweet tweet = new Tweet("Автоколонна МЧС России,доставившая гуманитарный груз на Донбасс,вернулась в Ногинский спасательный центр http://t.co/3LRtdd1JTT #мчс @MID_RF", configuration);
        for (String word : tweet) {
            System.out.println(word);
        }
    }

}
