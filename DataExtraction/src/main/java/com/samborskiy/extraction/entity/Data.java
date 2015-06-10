package com.samborskiy.extraction.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to keep configure information where and how get data for sample.
 *
 * @author Whiplash
 */
public class Data {

    private final String peopleNames;
    private final String screenNames;

    @JsonCreator
    public Data(@JsonProperty("peopleNames") String peopleNames,
                @JsonProperty("screenNames") String screenNames) {
        this.peopleNames = peopleNames;
        this.screenNames = screenNames;
    }

    @JsonProperty("peopleNames")
    public String getPeopleNames() {
        return peopleNames;
    }

    @JsonProperty("screenNames")
    public String getScreenNames() {
        return screenNames;
    }

    public List<String> extractPeopleNames() {
        return getData(peopleNames);
    }

    public List<String> extractScreenNames() {
        return getData(screenNames);
    }

    private static List<String> getData(String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(fileName))) {
            String str;
            while ((str = bf.readLine()) != null) {
                list.add(str);
            }
            bf.close();
        } catch (Exception ignored) {
        }
        return list;
    }

}
