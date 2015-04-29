package com.samborskiy.entity.instances.functions.account;

import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Whiplash on 29.04.2015.
 */
public class HashTagAttribute extends AccountFunction {

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
            for (String hashTag : getHashTags(account.get(i))) {
                int count = hashTags.getOrDefault(hashTag, 0);
                hashTags.put(hashTag, count + 1);
            }
        }
        return hashTags;
    }


    protected List<String> getHashTags(String tweet) {
        Pattern pattern = Pattern.compile("#(\\w)+");
        Matcher matcher = pattern.matcher(tweet);
        List<String> hashTags = new ArrayList<>();
        while (matcher.find()) {
            hashTags.add(matcher.group(0).toLowerCase());
        }
        return hashTags;
    }

    @Override
    protected Attribute getAttribute(double val, String... args) {
        return new Attribute(val, String.format("hash_tag_%s", args));
    }
}
