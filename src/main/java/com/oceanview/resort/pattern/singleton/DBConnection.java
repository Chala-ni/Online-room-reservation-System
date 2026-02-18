package com.oceanview.resort.pattern.singleton;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton Pattern - Database Connection Manager.
 * 
 * Ensures only one instance of the database connection manager exists
 * throughout the application lifecycle. This prevents excessive database
 * connections and centralizes connection configuration.
 * 
 * Design Pattern: Singleton (Creational)
 * - Private constructor prevents external instantiation
 * - Static getInstance() method provides global access point
 * - Thread-safe with synchronized block
 */
public class DBConnection {

    private static DBConnection instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    /**
     * Private constructor - loads database configuration from properties file.
     * This prevents external instantiation, enforcing the Singleton pattern.
     */
    private DBConnection() {
        try {
            Properties props = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            if (input != null) {
                props.load(input);
                this.url = props.getProperty("db.url");
                this.username = props.getProperty("db.username");
                this.password = props.getProperty("db.password");
                String driver = props.getProperty("db.driver");
                Class.forName(driver);
            } else {
                throw new RuntimeException("db.properties file not found in classpath");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database configuration", e);
        }
    }

    /**
     * Returns the single instance of DBConnection.
     * Uses double-checked locking for thread safety.
     */
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * Returns an active database connection.
     * Creates a new connection if the current one is null or closed.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    /**
     * Closes the current database connection.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
