package org.example.cybooks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    public static List<Loan> getLoans(int id_user) {
        List<Loan> borrowed_loans = new ArrayList<>();
        String query = "SELECT id_loan, isbn_book, id_member, loan_date, loan_duration FROM loan WHERE id_member=? ORDER BY DATE_ADD(loan_date, INTERVAL loan_duration DAY) ASC";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_user);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_loan = resultSet.getInt("id_loan");
                String isbn_book = resultSet.getString("isbn_book");
                int id_member = resultSet.getInt("id_member");
                Date loan_date = resultSet.getDate("loan_date");
                int loan_duration = resultSet.getInt("loan_duration");
                boolean expired = new Loan(id_loan, isbn_book, id_member, loan_date, loan_duration, false).getExpired();
                Loan loan = new Loan(id_loan, isbn_book, id_member, loan_date, loan_duration, expired);
                borrowed_loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowed_loans;
    }
}
