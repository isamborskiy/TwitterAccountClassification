package com.samborskiy.test.attrs;


import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.attributes.Attribute;
import com.samborskiy.entity.instances.attrs.Account;
import com.samborskiy.misc.AttrsInstancesFromDatabase;
import com.samborskiy.test.Statistics;
import com.samborskiy.test.attrs.testmachines.TestMachine;

import java.util.List;

/**
 * Created by Whiplash on 18.04.2015.
 */
public abstract class Test {

    protected final Configuration configuration;
    protected final int rounds;
    protected final int folds;

    public Test(Configuration configuration, int rounds, int folds) {
        this.configuration = configuration;
        this.rounds = rounds;
        this.folds = folds;
    }

    /**
     * Tests classifier by account entity.
     *
     * @return statistics of classifier test
     */
    public Statistics test(boolean isParallel) {
        List<Attribute> attributes = getAttributes();
        List<Account> instances = getAccountLists(attributes);
        TestMachine testMachine = getTestMachine(instances, isParallel);
        return testMachine.crossValidationTest(folds, rounds);
    }

    protected abstract List<Attribute> getAttributes();

    protected abstract TestMachine getTestMachine(List<Account> accounts, boolean isParallel);

    protected List<Account> getAccountLists(List<Attribute> attributes) {
        List<Account> accounts = AttrsInstancesFromDatabase.getAllAccounts(configuration);
        for (Account account : accounts) {
            attributes.forEach(account::addAttr);
        }
        return accounts;
    }
}
