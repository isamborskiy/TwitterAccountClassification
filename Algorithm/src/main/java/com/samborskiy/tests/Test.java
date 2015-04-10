package com.samborskiy.tests;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.AccountWithTweet;
import com.samborskiy.entity.instances.Tweet;
import com.samborskiy.statistic.Statistics;

import java.util.List;

/**
 * @author Whiplash
 */
public interface Test {

    public String getName();

    public Statistics crossValidationAccount(Configuration configuration, int foldCount, int round, List<Account> sample);

    public Statistics crossValidationTweet(Configuration configuration, int foldCount, int round, List<Tweet> sample);

    public Statistics crossValidationAccountByTweet(Configuration configuration, int foldCount, int round, List<AccountWithTweet> sample);
}
