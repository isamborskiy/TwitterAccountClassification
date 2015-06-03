package com.samborskiy.classifier;

import com.samborskiy.entity.Account;
import com.samborskiy.entity.Attribute;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

class AccountsToArff {

    private AccountsToArff() {
    }

    public static void write(Collection<Integer> types, List<Account> accounts, String relationName) throws FileNotFoundException {
        if (accounts == null || accounts.isEmpty()) {
            throw new IllegalArgumentException("data can't be null or empty");
        }
        try (PrintWriter writer = new PrintWriter(relationName + ".arff")) {
            printHeader(relationName, writer);
            printArgs(types, accounts, writer);
            printData(accounts, writer);
        }
    }

    private static void printData(List<Account> accounts, PrintWriter writer) {
        writer.println("@data");
        for (Account account : accounts) {
            for (Attribute attribute : account) {
                writer.format(Locale.US, "%f, ", attribute.getValue());
            }
            writer.println(account.getClassId());
        }
    }

    private static void printArgs(Collection<Integer> types, List<Account> accounts, PrintWriter writer) {
        Account account = accounts.get(0);
        for (Attribute attribute : account) {
            writer.println(attribute);
        }
        writer.format("@attribute class %s\n",
                types.stream().map(String::valueOf)
                        .collect(Collectors.joining(" ", "{", "}")));
        writer.println();
    }

    private static void printHeader(String relationshipName, PrintWriter writer) {
        writer.format("@relation %s\n", relationshipName);
        writer.println();
    }
}
