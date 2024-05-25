package org.cybooks;

import java.sql.*;

/**
 * Data Access Object (DAO) for Admin entities.
 * Handles database operations related to Admin entities.
 */
public class AdminDAO {

    /**
     * Retrieves an Admin entity from the database based on the provided email and password.
     *
     * @param email    the email address of the Admin
     * @param password the password of the Admin
     * @return the Admin entity if found, or null if not found
     */
    public static Admin getAdmin(String email, String password) {
        Admin admin = null;
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ? AND user_type='admin'")) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                admin = extractAdminFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    /**
     * Extracts an Admin entity from the given ResultSet.
     *
     * @param resultSet the ResultSet containing data retrieved from the database
     * @return an Admin entity extracted from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private static Admin extractAdminFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_user");
        String lastname = resultSet.getString("lastname");
        String firstname = resultSet.getString("firstname");
        String email = resultSet.getString("email");
        Date inscriptionDate = resultSet.getDate("inscriptionDate");
        String state = resultSet.getString("state");
        Date birthday = resultSet.getDate("birthday");
        String phone = resultSet.getString("phone");
        String sex = resultSet.getString("sex");
        String password = resultSet.getString("password");
        return new Admin(id, lastname, firstname, email, inscriptionDate, state, birthday, phone, sex, password);
    }
}
