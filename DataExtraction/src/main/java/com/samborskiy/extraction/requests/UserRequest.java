package com.samborskiy.extraction.requests;

import com.samborskiy.extraction.utils.TwitterHelper;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Request to get information about user.
 *
 * @author Whiplash
 */
public class UserRequest extends Request<User> {

    private final String screenName;
    private final boolean isCorporate;

    /**
     * Creates instance of {@code FindUsersRequest}.
     *
     * @param twitterHelper to get access to twitter API
     * @param screenName    screen name of user
     * @param isCorporate   is corporate account or personal
     */
    public UserRequest(TwitterHelper twitterHelper, String screenName, boolean isCorporate) {
        super(twitterHelper);
        this.screenName = screenName;
        this.isCorporate = isCorporate;
    }

    @Override
    protected User run() throws TwitterException {
        return getTwitterHelper().getUser(screenName, isCorporate);
    }

}
