package com.samborskiy.weka;

import com.samborskiy.entity.Configuration;
import com.samborskiy.entity.Account;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.instances.Instance;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by Whiplash on 23.04.2015.
 */
class DataToArff {

    private DataToArff() {
    }

    public static void write(Configuration configuration, List<Account> data, String relationshipName) throws FileNotFoundException {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("data can't be null or empty");
        }
        try (PrintWriter writer = new PrintWriter(relationshipName + ".arff")) {
            printHeader(configuration, data, relationshipName, writer);
            printArgs(configuration, data, relationshipName, writer);
            printData(configuration, data, relationshipName, writer);
        }
    }

    private static void printData(Configuration configuration, List<Account> data, String relationshipName, PrintWriter writer) {
        writer.println("@data");
        for (Instance instance : data) {
            for (Attribute attribute : instance) {
                writer.format(Locale.US, "%f, ", attribute.getValue());
            }
            writer.println(instance.getClassId());
        }
    }

    private static void printArgs(Configuration configuration, List<Account> data, String relationshipName, PrintWriter writer) {
        Instance instance = data.get(0);
        for (Attribute attribute : instance) {
            writer.println(attribute);
        }
        String nominal = String.format("{%s}", String.join(", ", configuration.getTypes().stream().map(i -> String.valueOf(i.getId())).collect(Collectors.toList())));
        writer.format("@attribute class %s\n", nominal);
        writer.println();
    }

    private static void printHeader(Configuration configuration, List<Account> data, String relationshipName, PrintWriter writer) {
        writer.format("@relation %s\n", relationshipName);
        writer.println();
    }
}
