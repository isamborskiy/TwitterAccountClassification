package com.samborskiy.entity.instances.functions.account;

import com.samborskiy.entity.analyzers.sentence.TweetParser;
import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.Attribute;

import java.util.*;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class VocabularyAttribute extends AccountFunction {

    private TweetParser parser = TweetParser.get();

    @Override
    public void apply(List<Account> accounts) {
        Set<String> hashTags = new HashSet<>();
        List<Map<String, Integer>> hashTagsList = new ArrayList<>();
        for (Account account : accounts) {
            Map<String, Integer> accountHashTags = getHashTags(account);
            hashTagsList.add(accountHashTags);
            hashTags.addAll(accountHashTags.keySet());
        }
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            Map<String, Integer> accountHashTags = hashTagsList.get(i);
            for (String hashTag : hashTags) {
                account.addAttr(getAttribute(accountHashTags.getOrDefault(hashTag, 0), hashTag));
            }
        }
    }

    private Map<String, Integer> getHashTags(Account account) {
        Map<String, Integer> hashTags = new HashMap<>();
        for (int i = 0; i < account.size(); i++) {
            for (String word : parser.parse(account.get(i).toLowerCase())) {
                int count = hashTags.getOrDefault(word, 0);
                hashTags.put(word, count + 1);
            }
        }
        return hashTags;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, String.format("word_%s", args));
    }
}
