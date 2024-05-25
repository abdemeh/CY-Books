package org.cybooks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Database class provides methods to establish a connection to the database.
 */
public class Database {
    /**
     * The URL for connecting to the database.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/cy_books";

    /**
     * The username for accessing the database.
     */
    private static final String USER = "root";

    /**
     * The password for accessing the database.
     */
    private static final String PASSWORD = "";

    /**
     * Establishes a connection to the database using the specified URL, username, and password.
     *
     * @return a connection to the database
     * @throws SQLException if a database access error occurs or the URL, username, or password is invalid
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
