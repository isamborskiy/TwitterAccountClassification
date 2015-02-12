package com.samborskiy.extraction.utils;

import com.samborskiy.extraction.Configuration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Class which help user interact with database.
 *
 * @author Whiplash
 */
public class DatabaseHelper implements AutoCloseable {

    private static final String DATABASE_URL = "jdbc:sqlite:%s/%s.db";

    private static final String TABLE_NAME = "twitter_data";
    private static final String USER_ID = "user_id";
    private static final String SCREEN_NAME = "screen_name";
    public static final String TWEETS = "tweets";
    // 0 - personal account, 1 - corporate account
    public static final String ACCOUNT_TYPE = "account_type";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + USER_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + SCREEN_NAME + " VARCHAR(40) UNIQUE NOT NULL, "
            + TWEETS + " TEXT, "
            + ACCOUNT_TYPE + " INTEGER NOT NULL);";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?);";
    private static final String SELECT_QUERY_USER_ID = "SELECT %s FROM " + TABLE_NAME + " WHERE " + USER_ID + " = %d;";
    private static final String SELECT_QUERY_SCREEN_NAME = "SELECT %s FROM " + TABLE_NAME + " WHERE " + SCREEN_NAME + " = \'%s\';";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME + ";";

    private Configuration configuration;
    private Connection connection;

    /**
     * Creates new instance of {@code DatabaseHelper} with
     * initialize {@code configuration}.
     *
     * @param configuration needed to create new database or connect to existing
     * @throws java.lang.Exception see {@link #connect()}
     */
    public DatabaseHelper(Configuration configuration) throws Exception {
        this.configuration = configuration;
        connect();
    }

    /**
     * Creates a database connection.
     *
     * @throws java.lang.Exception if connection is failed (e.g. bad {@code databasePath} in configuration)
     */
    private void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        File databasePath = new File(configuration.getDatabasePath());
        if (databasePath.exists() || databasePath.mkdirs()) {
            String databaseUrl = String.format(DATABASE_URL, configuration.getDatabasePath(), configuration.getLang());
            connection = DriverManager.getConnection(databaseUrl);
        }
    }

    /**
     * Creates table {@link #TABLE_NAME} if this table not exists.
     *
     * @return {@code true} if table is create
     */
    public boolean createTable() {
        try (Statement statement = connection.createStatement()) {
            return statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserts value into table.
     *
     * @param userId      user's twitter id
     * @param screenName  account owner screen name
     * @param tweets      user's tweets
     * @param accountType {@code 0} if it personal account else {@code 1}
     * @return {@code true} if insert is complete
     */
    public boolean insert(long userId, String screenName, String tweets, int accountType) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setLong(1, userId);
            statement.setString(2, screenName);
            statement.setString(3, tweets);
            statement.setInt(4, accountType);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserts all values into table.
     *
     * @param userIds     users' twitter ids
     * @param screenNames accounts owner screen name
     * @param tweets      users' tweets
     * @param accountType {@code 0} if it personal account else {@code 1}
     * @return {@code true} if insert is complete
     */
    public boolean insertAll(List<Long> userIds, List<String> screenNames, List<String> tweets, int accountType) {
        for (int i = 1; i < userIds.size(); i++) {
            insert(userIds.get(i), screenNames.get(i), tweets.get(i), accountType);
        }
        return true;
    }

    /**
     * Returns user screen name.
     *
     * @param userId user's twitter id
     * @return user screen name
     */
    public String getScreenName(long userId) {
        try (Statement statement = connection.createStatement()) {
            String selectQuery = String.format(SELECT_QUERY_USER_ID, SCREEN_NAME, userId);
            ResultSet set = statement.executeQuery(selectQuery);
            return set.getString(SCREEN_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns user tweets.
     *
     * @param userId user's twitter id
     * @return user tweets
     */
    public String getTweets(long userId) {
        try (Statement statement = connection.createStatement()) {
            String selectQuery = String.format(SELECT_QUERY_USER_ID, TWEETS, userId);
            ResultSet set = statement.executeQuery(selectQuery);
            return set.getString(TWEETS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns account type.
     *
     * @param userId user's twitter id
     * @return account type
     */
    public int getAccountType(long userId) {
        try (Statement statement = connection.createStatement()) {
            String selectQuery = String.format(SELECT_QUERY_USER_ID, ACCOUNT_TYPE, userId);
            ResultSet set = statement.executeQuery(selectQuery);
            return set.getInt(ACCOUNT_TYPE);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Returns user tweets.
     *
     * @param screenName screen name of user
     * @return user tweets
     */
    public String getTweets(String screenName) {
        try (Statement statement = connection.createStatement()) {
            String selectQuery = String.format(SELECT_QUERY_SCREEN_NAME, TWEETS, screenName);
            ResultSet set = statement.executeQuery(selectQuery);
            return set.getString(TWEETS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns account type.
     *
     * @param screenName screen name of user
     * @return account type
     */
    public int getAccountType(String screenName) {
        try (Statement statement = connection.createStatement()) {
            String selectQuery = String.format(SELECT_QUERY_SCREEN_NAME, ACCOUNT_TYPE, screenName);
            ResultSet set = statement.executeQuery(selectQuery);
            return set.getInt(ACCOUNT_TYPE);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Returns {@link java.sql.ResultSet} with all table data.
     * ATTENTION: you must close the cursor yourself (call method close())
     *
     * @return all table data
     */
    public ResultSet getAll() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(SELECT_ALL_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
