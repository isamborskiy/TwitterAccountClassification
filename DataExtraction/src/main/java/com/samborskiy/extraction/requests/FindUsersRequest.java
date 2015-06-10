package com.samborskiy.extraction.requests;

import com.samborskiy.extraction.utils.TwitterHelper;
import twitter4j.TwitterException;
import twitter4j.User;

import java.util.List;

/**
 * Request to find list of user with {@code name}.
 *
 * @author Whiplash
 */
public class FindUsersRequest extends Request<List<User>> {

    private final String name;

    /**
     * Creates instance of {@code FindUsersRequest}.
     *
     * @param twitterHelper to get access to twitter API
     * @param name          name by which you want to search
     */
    public FindUsersRequest(TwitterHelper twitterHelper, String name) {
        super(twitterHelper);
        this.name = name;
    }

    @Override
    protected List<User> run() throws TwitterException {
        return getTwitterHelper().findUsersByName(name);
    }

}
