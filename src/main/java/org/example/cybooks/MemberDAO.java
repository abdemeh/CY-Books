package org.example.cybooks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    public static List<Member> searchMembers(String search) {
        List<Member> members = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Member WHERE CONCAT(firstname, ' ', lastname) LIKE ? OR CONCAT(lastname, ' ', firstname) LIKE ? OR email LIKE ? OR phone LIKE ?")) {
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
    public static Member getMemberById(int memberId) {
        Member member = null;
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Member WHERE id_member = ?")) {
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

    public static List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Member");
            while (resultSet.next()) {
                Member member = extractMemberFromResultSet(resultSet);
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public static void addMember(Member member) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Member (lastname, firstname, email, inscriptionDate, state, birthday, phone, sex) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
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


    public static void deleteMember(int memberId) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Member WHERE id_member = ?")) {
            statement.setInt(1, memberId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMember(Member member) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Member SET lastname = ?, firstname = ?, email = ?, birthday = ?, phone = ?, sex = ? WHERE id_member = ?")) {
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

    private static Member extractMemberFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_member");
        String lastname = resultSet.getString("lastname");
        String firstname = resultSet.getString("firstname");
        String email = resultSet.getString("email");
        Date inscriptionDate = resultSet.getDate("inscriptionDate");
        String state = resultSet.getString("state");
        Date birthday = resultSet.getDate("birthday");
        String phone = resultSet.getString("phone");
        String sex = resultSet.getString("sex");
        return new Member(id, lastname, firstname, email, inscriptionDate, state, birthday, phone, sex);
    }
}
