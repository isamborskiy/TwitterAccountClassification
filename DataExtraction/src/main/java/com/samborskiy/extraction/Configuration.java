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
    private int tweetPerUser;
    private String databasePath;
    private String[] corporateTwitterAccounts;

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

    public int getTweetPerUser() {
        return tweetPerUser;
    }

    public void setTweetPerUser(int tweetPerUser) {
        this.tweetPerUser = tweetPerUser;
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
}
