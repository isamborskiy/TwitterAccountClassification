package com.samborskiy.entity.instances;

import com.samborskiy.entity.functions.AccountFunction;
import com.samborskiy.entity.utils.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Whiplash on 21.04.2015.
 */
public class Account extends Instance {

    protected static final String URL_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    protected static final String INSTAGRAM = "instag";

    protected final List<String> tweets;

    public Account(int classId, String screenName, DatabaseHelper dbHelper) {
        super(classId);
        tweets = new ArrayList<>();
        try (ResultSet set = dbHelper.getUser(screenName)) {
            initAttrs(set);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initAttrs(ResultSet query) throws SQLException {
        attrs.add(new Attribute(query.getInt(DatabaseHelper.FOLLOWERS), DatabaseHelper.FOLLOWERS));
        attrs.add(new Attribute(query.getInt(DatabaseHelper.FOLLOWING), DatabaseHelper.FOLLOWING));
        attrs.add(new Attribute(query.getInt(DatabaseHelper.VERIFIED), DatabaseHelper.VERIFIED));
        attrs.add(new Attribute(query.getInt(DatabaseHelper.FAVOURITE), DatabaseHelper.FAVOURITE));
    }

    public int size() {
        return tweets.size();
    }

    public String get(int i) {
        return tweets.get(i);
    }

    @Override
    public void addAttr(AccountFunction accountFunction) {
        attrs.addAll(accountFunction.apply(tweets));
    }

    public void addTweet(String tweet) {
        tweets.add(tweet.replaceAll(URL_REGEX, " "));
    }

    private int instagramCount(String tweet) {
        int instagramCount = 0;
        int lastIndex = 0;
        while ((lastIndex = tweet.indexOf(INSTAGRAM, lastIndex + 1)) != -1) {
            instagramCount++;
            lastIndex += INSTAGRAM.length();
        }
        return instagramCount;
    }

    private int urlsCount(String tweet) {
        int urlCount = 0;
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(tweet);
        while (matcher.find()) {
            urlCount++;
        }
        return urlCount;
    }

    public void addTweets(List<String> tweets) {
        float instagramPerTweet = 0;
        float tweetsWithInstagram = 0;
        float urlsPerTweet = 0;
        float tweetsWithUrls = 0;
        for (String tweet : tweets) {
            int instagramNumber = instagramCount(tweet);
            instagramPerTweet += instagramNumber;
            if (instagramNumber > 0) {
                tweetsWithInstagram++;
            }

            int urlsNumber = urlsCount(tweet);
            urlsPerTweet += urlsNumber;
            if (urlsNumber > 0) {
                tweetsWithUrls++;
            }

            addTweet(tweet);
        }
        attrs.add(new Attribute(instagramPerTweet / tweets.size(), "instagram_per_tweet"));
        attrs.add(new Attribute(tweetsWithInstagram / tweets.size(), "tweets_with_instagram"));
        attrs.add(new Attribute(urlsPerTweet / tweets.size(), "urls_per_tweet"));
        attrs.add(new Attribute(tweetsWithUrls / tweets.size(), "tweets_with_urls"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return !(tweets != null ? !tweets.equals(account.tweets) : account.tweets != null);
    }

    @Override
    public int hashCode() {
        return tweets != null ? tweets.hashCode() : 0;
    }
}
