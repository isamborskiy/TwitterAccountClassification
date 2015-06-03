package com.samborskiy.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by Whiplash on 21.04.2015.
 */
public class Account implements Iterable<Attribute> {

    protected final int classId;
    protected final List<String> tweets = new ArrayList<>();
    protected final List<Attribute> attrs = new ArrayList<>();

    protected int followers;
    protected int following;
    protected int verified;
    protected int favourite;

    public Account(int classId, int followers, int following, int verified, int favourite) {
        this.classId = classId;
        this.followers = followers;
        this.following = following;
        this.verified = verified;
        this.favourite = favourite;
    }

    public int size() {
        return tweets.size();
    }

    public void addTweet(String tweet) {
        tweets.add(tweet);
    }

    public void addTweets(List<String> tweets) {
        tweets.forEach(this::addTweet);
    }

    public void addAttr(Attribute attribute) {
        attrs.add(attribute);
    }

    public void addAttrs(List<Attribute> attributes) {
        attributes.forEach(this::addAttr);
    }

    public List<String> getTweets() {
        return tweets;
    }

    public String get(int i) {
        return tweets.get(i);
    }

    public int getClassId() {
        return classId;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public int getVerified() {
        return verified;
    }

    public int getFavourite() {
        return favourite;
    }

    @Override
    public Iterator<Attribute> iterator() {
        return attrs.iterator();
    }

    @Override
    public void forEach(Consumer<? super Attribute> action) {
        attrs.forEach(action);
    }

    @Override
    public Spliterator<Attribute> spliterator() {
        return attrs.spliterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return tweets.equals(account.tweets);
    }

    @Override
    public int hashCode() {
        return tweets != null ? tweets.hashCode() : 0;
    }
}
