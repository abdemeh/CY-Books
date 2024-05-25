package org.cybooks;

import java.sql.*;

/**
 * The Database class provides methods to establish a connection to the database and manage database operations.
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

    /**
     * Creates necessary tables if they don't exist in the database.
     *
     * @throws SQLException if a database access error occurs or the URL, username, or password is invalid
     */
    public static void checkDatabaseTables() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            // Drop and create loan table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `loan` ("
                    + "`id_loan` int NOT NULL AUTO_INCREMENT,"
                    + "`isbn_book` varchar(20) NOT NULL,"
                    + "`id_member` int NOT NULL,"
                    + "`loan_date` date NOT NULL,"
                    + "`loan_duration` int NOT NULL,"
                    + "PRIMARY KEY (`id_loan`),"
                    + "KEY `fk_loan_member` (`id_member`))");

            // Drop and create users table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `users` ("
                    + "`id_user` int NOT NULL AUTO_INCREMENT,"
                    + "`lastname` varchar(255) NOT NULL,"
                    + "`firstname` varchar(255) NOT NULL,"
                    + "`email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,"
                    + "`password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,"
                    + "`user_type` varchar(20) NOT NULL DEFAULT 'member',"
                    + "`inscriptionDate` date NOT NULL,"
                    + "`state` varchar(20) NOT NULL,"
                    + "`block_till` date DEFAULT NULL,"
                    + "`birthday` date NOT NULL,"
                    + "`phone` varchar(20) NOT NULL,"
                    + "`sex` char(1) NOT NULL,"
                    + "PRIMARY KEY (`id_user`))");

            // Insert a user if the users table is newly created (doesn't exist)
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM `users` WHERE user_type='admin'");
            rs.next();
            int count = rs.getInt("count");
            if (count == 0) {
                String query = "INSERT INTO `users` "
                        + "(`lastname`, `firstname`, `email`, `password`, `user_type`, `inscriptionDate`, `state`, `birthday`, `phone`, `sex`) "
                        + "VALUES "
                        + "('Admin', 'Admin', 'admin@cy-tech.fr', '1234', 'admin' , ?, 'Actif', '2000-01-01', '+3370000000', 'M')";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                // Set the inscriptionDate parameter
                preparedStatement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
                // Execute the PreparedStatement
                preparedStatement.executeUpdate();
            }
        }
    }
}
