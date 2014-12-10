package com.samborskiy.extraction.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.samborskiy.extraction.Configuration;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class which help user interact with twitter API.
 *
 * @author Whiplash
 */
public class TwitterHelper {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    private Configuration configuration;
    private Twitter twitter;

    /**
     * Creates new instance of {@code TwitterHelper} with
     * initialize {@code configuration}.
     *
     * @param configuration needed to connect to database and get
     *                      some parameters to data extraction
     */
    public TwitterHelper(Configuration configuration) {
        this.configuration = configuration;
        twitter = new TwitterFactory().getInstance();
    }

    /**
     * Returns information about user by screen name.
     *
     * @param screenName  screen name of user
     * @param isCorporate returns the user found despite constraints of {@link #isCorrectUser(twitter4j.User)}
     * @return information about user
     */
    public User getUser(String screenName, boolean isCorporate) {
        try {
            User user = twitter.showUser(screenName);
            if (isCorporate || isCorrectUser(user)) {
                return user;
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns user tweets in json format.
     *
     * @param screenName  screen name of user
     * @param tweetNumber number of tweet per user
     * @return user tweets
     */
    public String getTweets(String screenName, int tweetNumber) {
        try {
            Paging paging = new Paging(1, tweetNumber);
            List<Status> statuses = twitter.getUserTimeline(screenName, paging);
            StringWriter str = new StringWriter();
            mapper.writeValue(str, filterTweets(statuses));
            return str.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns filtered tweets of user.
     *
     * @param statuses user statuses (tweets)
     * @return user tweets
     */
    private List<String> filterTweets(List<Status> statuses) {
        List<String> tweets = new ArrayList<>();
        for (Status status : statuses) {
            if (!status.isRetweet() && status.getLang().equals(configuration.getLang())) {
                tweets.add(status.getText());
            }
        }
        return tweets;
    }

    /**
     * Returns list of users which was found by name.
     *
     * @param name name of user
     * @return list of users
     */
    public List<User> findUsersByName(String name) {
        try {
            Set<User> filteredUser = new HashSet<>();
            for (int page = 0; page < 5; page++) {
                filteredUser.addAll(getUsersPageByName(name, page));
            }
            return new ArrayList<>(filteredUser);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns list of users from {@code page} by name.
     *
     * @param name name of user
     * @param page number of page
     * @throws Exception if twitter service or network is unavailable
     */
    private List<User> getUsersPageByName(String name, int page) throws Exception {
        List<User> filteredUser = new ArrayList<>();
        ResponseList<User> users = twitter.searchUsers(getQuery(name), page);
        for (User user : users) {
            if (isCorrectUser(user)) {
                filteredUser.add(user);
            }
        }
        return filteredUser;
    }

    /**
     * Returns encoded query to {@code UTF-8}.
     *
     * @param name name of user
     * @return resulting query
     * @throws java.io.UnsupportedEncodingException if the named encoding is not supported
     */
    private String getQuery(String name) throws UnsupportedEncodingException {
        return "q=" + URLEncoder.encode(name, "UTF-8");
    }

    /**
     * Returns all followers of user.
     *
     * @param user user from whom you want to request followers
     * @return user's followers
     */
    public List<User> getFollowers(User user) {
        long cursor = -1;
        PagableResponseList<User> followers;
        List<User> users = new ArrayList<>();
        try {
            do {
                followers = twitter.getFollowersList(user.getId(), cursor);
                users.addAll(filterFollowers(followers));
            } while ((cursor = followers.getNextCursor()) != 0);
            return users;
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns list of filtered users.
     *
     * @param followers followers of user
     * @return list of followers
     */
    private List<User> filterFollowers(PagableResponseList<User> followers) {
        List<User> users = new ArrayList<>();
        for (User follower : followers) {
            if (isCorrectUser(follower)) {
                users.add(follower);
            }
        }
        return users;
    }

    /**
     * Checks is correct user or not.
     *
     * @param user user whom will be checked
     * @return {@code true} if user is correct
     */
    private boolean isCorrectUser(User user) {
        return user.getLang().equals(configuration.getLang()) && !user.isProtected()
                && user.getStatus() != null;
    }

}
