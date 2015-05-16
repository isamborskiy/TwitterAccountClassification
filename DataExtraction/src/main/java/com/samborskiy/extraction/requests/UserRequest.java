package com.samborskiy.extraction.requests;

import com.samborskiy.entity.utils.TwitterHelper;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Request to get information about user.
 *
 * @author Whiplash
 */
public class UserRequest extends Request<User> {

    private final String screenName;
    private final boolean constraints;

    /**
     * Creates instance of {@code FindUsersRequest}.
     *
     * @param twitterHelper to get access to twitter API
     * @param screenName    screen name of user
     * @param constraints   is corporate account or personal
     */
    public UserRequest(TwitterHelper twitterHelper, String screenName, boolean constraints) {
        super(twitterHelper);
        this.screenName = screenName;
        this.constraints = constraints;
    }

    @Override
    protected User run() throws TwitterException {
        return getTwitterHelper().getUser(screenName, constraints);
    }

}
