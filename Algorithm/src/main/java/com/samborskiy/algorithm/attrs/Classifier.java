package com.samborskiy.algorithm.attrs;

import com.samborskiy.entity.instances.attrs.Account;

import java.util.List;

/**
 * Created by Whiplash on 22.04.2015.
 */
public abstract class Classifier {

    protected final List<Account> data;
    protected final int size;
    protected final int classNumber;

    public Classifier(List<Account> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("data must be not empty");
        }
        this.data = data;
        this.size = data.size();
        this.classNumber = data.get(0).getClassId();
    }

    protected List<Account> getData() {
        return data;
    }

    protected Account get(int i) {
        return data.get(i);
    }

    protected int getClassNumber() {
        return classNumber;
    }

    public abstract void train();


    public abstract int getClassId(Account account);
}
