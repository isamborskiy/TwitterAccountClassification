package com.samborskiy.extraction.requests;

import com.samborskiy.entity.utils.TwitterHelper;
import twitter4j.TwitterException;

/**
 * Request to get user's retweets.
 *
 * @author Whiplash
 */
public class RetweetsRequest extends Request<Float> {

    private final String screenName;
    private final int number;

    /**
     * Creates instance of {@code FindUsersRequest}.
     *
     * @param twitterHelper to get access to twitter API
     * @param user          user who will take tweets
     * @param number        number of required tweets
     */
    public RetweetsRequest(TwitterHelper twitterHelper, String screenName, int number) {
        super(twitterHelper);
        this.screenName = screenName;
        this.number = number;
    }

    @Override
    protected Float run() throws TwitterException {
//        try {
//            if (new UserRequest(getTwitterHelper(), screenName, false).make() != null) {
        return getTwitterHelper().getRetweetNumber(screenName, number);
//            }
//        } catch (InterruptedException ignore) {
//        }
//        System.out.format("%s is protected =(\n", screenName);
//        return 0f;
    }

}
