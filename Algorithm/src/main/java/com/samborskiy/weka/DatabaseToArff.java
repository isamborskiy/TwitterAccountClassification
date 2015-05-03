package com.samborskiy.weka;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.instances.Account;
import com.samborskiy.entity.instances.functions.AttributeFunction;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Whiplash on 23.04.2015.
 */
public class DatabaseToArff {

    private DatabaseToArff() {
    }

    public static void write(Configuration configuration, String relationName, List<AttributeFunction> functions) throws FileNotFoundException {
        List<Account> data = DatabaseToData.getAllAccounts(configuration);
        for (Account account : data) {
            functions.forEach(account::addAttr);
        }
        DataToArff.write(configuration, data, relationName);
    }
}
