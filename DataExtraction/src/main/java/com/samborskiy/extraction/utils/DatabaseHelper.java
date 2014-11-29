package com.samborskiy.extraction.utils;

import com.samborskiy.extraction.Configuration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Class which help user interact with database.
 *
 * @author Whiplash
 */
public class DatabaseHelper implements AutoCloseable {

    private static final String DATABASE_URL = "jdbc:sqlite:%s/%s.db";

    private static final String TABLE_NAME = "twitter_data";
    private static final String SCREEN_NAME = "screen_name";
    private static final String TWEETS = "tweets";
    // 0 - personal account, 1 - corporate account
    private static final String ACCOUNT_TYPE = "account_type";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + SCREEN_NAME + " VARCHAR(40) PRIMARY KEY NOT NULL, "
            + TWEETS + " TEXT NOT NULL, "
            + ACCOUNT_TYPE + " INTEGER NOT NULL);";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES %s;";
    private static final String INSERT_TUPLE = "(\'%s\', \'%s\', %d)";
    private static final String SELECT_QUERY = "SELECT %s FROM " + TABLE_NAME + " WHERE " + SCREEN_NAME + " = \'%s\';";

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
     * @param screenName  account owner screen name
     * @param tweets      user's tweets (without retweet)
     * @param accountType {@code 0} if it personal account else {@code 1}
     * @return {@code true} if insert is complete
     */
    public boolean insert(String screenName, String tweets, int accountType) {
        try (Statement statement = connection.createStatement()) {
            String insertTupleValue = String.format(INSERT_TUPLE, screenName, tweets, accountType);
            String insertQuery = String.format(INSERT_QUERY, insertTupleValue);
            return statement.execute(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserts all values into table.
     *
     * @param screenNames  accounts owner screen name
     * @param tweets       users' tweets (without retweet)
     * @param accountTypes {@code 0} if it personal account else {@code 1}
     * @return {@code true} if insert is complete
     */
    public boolean insertAll(String[] screenNames, String[] tweets, int[] accountTypes) {
        try (Statement statement = connection.createStatement()) {
            StringBuilder insertTupleValues = new StringBuilder();
            insertTupleValues.append(String.format(INSERT_TUPLE, screenNames[0], tweets[0], accountTypes[0]));
            for (int i = 1; i < screenNames.length; i++) {
                insertTupleValues.append(", ").append(String.format(INSERT_TUPLE, screenNames[i], tweets[i], accountTypes[i]));
            }
            String insertQuery = String.format(INSERT_QUERY, insertTupleValues.toString());
            return statement.execute(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            String selectQuery = String.format(SELECT_QUERY, TWEETS, screenName);
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
            String selectQuery = String.format(SELECT_QUERY, ACCOUNT_TYPE, screenName);
            ResultSet set = statement.executeQuery(selectQuery);
            return set.getInt(ACCOUNT_TYPE);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
