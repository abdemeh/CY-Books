package org.cybooks;

import java.sql.*;

public class AdminDAO {
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
