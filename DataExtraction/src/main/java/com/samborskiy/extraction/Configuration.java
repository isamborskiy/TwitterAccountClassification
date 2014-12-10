package com.samborskiy.extraction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Class stores the configuration for the selected language.
 *
 * @author Whiplash
 */
public class Configuration {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    private String lang;
    private int personalTweetPerUser;
    private int corporateTweetPerUser;
    private int numberOfPersonalAccounts;
    private String databasePath;
    private String[] corporateTwitterAccounts;
    private String manNames;
    private String womanNames;

    /**
     * Builds new instance of {@code Configuration} parsing {@code jsonData}.
     *
     * @param configFile configuration file (in json format)
     * @return {@code Configuration} constructed from json
     */
    public static Configuration build(File configFile) {
        try {
            byte[] jsonData = Files.readAllBytes(configFile.toPath());
            return mapper.readValue(jsonData, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getPersonalTweetPerUser() {
        return personalTweetPerUser;
    }

    public void setPersonalTweetPerUser(int personalTweetPerUser) {
        this.personalTweetPerUser = personalTweetPerUser;
    }

    public int getCorporateTweetPerUser() {
        return corporateTweetPerUser;
    }

    public void setCorporateTweetPerUser(int corporateTweetPerUser) {
        this.corporateTweetPerUser = corporateTweetPerUser;
    }

    public int getNumberOfPersonalAccounts() {
        return numberOfPersonalAccounts;
    }

    public void setNumberOfPersonalAccounts(int numberOfPersonalAccounts) {
        this.numberOfPersonalAccounts = numberOfPersonalAccounts;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public String[] getCorporateTwitterAccounts() {
        return corporateTwitterAccounts;
    }

    public void setCorporateTwitterAccounts(String[] corporateTwitterAccounts) {
        this.corporateTwitterAccounts = corporateTwitterAccounts;
    }

    public String getManNames() {
        return manNames;
    }

    public void setManNames(String manNames) {
        this.manNames = manNames;
    }

    public String getWomanNames() {
        return womanNames;
    }

    public void setWomanNames(String womanNames) {
        this.womanNames = womanNames;
    }
}
