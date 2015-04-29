package com.samborskiy.entity.instances.functions.account;

import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.Attribute;

import java.util.List;

/**
 * Created by Whiplash on 29.04.2015.
 */
public abstract class AccountFunction {

    public abstract void apply(List<Account> accounts);

    protected abstract Attribute getAttribute(double val, String... args);
}
