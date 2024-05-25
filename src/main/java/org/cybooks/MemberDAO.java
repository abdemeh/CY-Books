package org.cybooks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing Member entities in the database.
 * Provides methods for CRUD (Create, Read, Update, Delete) operations on Member objects.
 */
public class MemberDAO {

    /**
     * Searches for members based on a search term.
     *
     * @param search The search term to match against member names, email, or phone number.
     * @return A list of members matching the search criteria.
     */
    public static List<Member> searchMembers(String search) {
        List<Member> members = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users WHERE (CONCAT(firstname, ' ', lastname) LIKE ? OR CONCAT(lastname, ' ', firstname) LIKE ? OR email LIKE ? OR phone LIKE ?) AND user_type='member'")) {
            // Set parameters for prepared statement with wildcard for search term
            String searchTerm = "%" + search.trim() + "%";
            statement.setString(1, searchTerm);
            statement.setString(2, searchTerm);
            statement.setString(3, searchTerm);
            statement.setString(4, searchTerm);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Member member = extractMemberFromResultSet(resultSet);
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    /**
     * Retrieves a member by their ID.
     *
     * @param memberId The ID of the member to retrieve.
     * @return The member with the specified ID, or null if not found.
     */
    public static Member getMemberById(int memberId) {
        Member member = null;
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id_user = ?")) {
            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                member = extractMemberFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    /**
     * Retrieves all members from the database.
     *
     * @return A list of all members.
     */
    public static List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE user_type='member'");
            while (resultSet.next()) {
                Member member = extractMemberFromResultSet(resultSet);
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    /**
     * Adds a new member to the database.
     *
     * @param member The member to add.
     */
    public static void addMember(Member member) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (lastname, firstname, email, inscriptionDate, state, birthday, phone, sex) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, member.getLastName());
            statement.setString(2, member.getFirstName());
            statement.setString(3, member.getEmail());
            statement.setDate(4, new java.sql.Date(member.getInscriptionDate().getTime()));
            statement.setString(5, member.getState());
            statement.setDate(6, new java.sql.Date(member.getBirthday().getTime()));
            statement.setString(7, member.getPhone());
            statement.setString(8, member.getSex());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a member from the database.
     *
     * @param memberId The ID of the member to delete.
     */
    public static void deleteMember(int memberId) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id_user = ?")) {
            statement.setInt(1, memberId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing member in the database.
     *
     * @param member The member with updated information.
     */
    public static void updateMember(Member member) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE users SET lastname = ?, firstname = ?, email = ?, birthday = ?, phone = ?, sex = ? WHERE id_user = ?")) {
            statement.setString(1, member.getLastName());
            statement.setString(2, member.getFirstName());
            statement.setString(3, member.getEmail());
            statement.setDate(4, new java.sql.Date(member.getBirthday().getTime()));
            statement.setString(5, member.getPhone());
            statement.setString(6, member.getSex());
            statement.setInt(7, member.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts a Member object from the ResultSet obtained from a database query.
     *
     * @param resultSet The ResultSet containing member information.
     * @return A Member object extracted from the ResultSet.
     * @throws SQLException If an SQL exception occurs.
     */
    private static Member extractMemberFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_user");
        String lastname = resultSet.getString("lastname");
        String firstname = resultSet.getString("firstname");
        String email = resultSet.getString("email");
        Date inscriptionDate = resultSet.getDate("inscriptionDate");
        String state = resultSet.getString("state");
        Date birthday = resultSet.getDate("birthday");
        String phone = resultSet.getString("phone");
        String sex = resultSet.getString("sex");
        Date block_till = resultSet.getDate("block_till");
        return new Member(id, lastname, firstname, email, inscriptionDate, state, birthday, phone, sex, block_till);
    }
}
