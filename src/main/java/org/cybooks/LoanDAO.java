package org.cybooks;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    public static boolean addNewLoan(String isbnBook, int idMember) {
        boolean s = false;
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //System.out.println("INSERT INTO loan (id_member, isbn_book, loan_date) VALUES ("+idMember+", "+isbnBook+", "+today.format(formatter)+")");
        if (verifyIfUserHaveBorrowedBook(isbnBook,idMember)) {
            s = false;
        }else{
            String insertLoanSQL = "INSERT INTO loan (isbn_book, id_member, loan_date, loan_duration) VALUES (?, ?, ?, ?)";

            try (Connection connection = Database.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertLoanSQL)) {

                int loanDuration = 15;

                preparedStatement.setString(1, isbnBook);
                preparedStatement.setInt(2, idMember);
                preparedStatement.setDate(3, Date.valueOf(today));
                preparedStatement.setInt(4, loanDuration);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    s = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return s;
    }
    public static boolean verifyIfUserHaveBorrowedBook(String isbnBook, int idMember) {
        boolean userHasBorrowedBook = false;

        String query = "SELECT COUNT(*) AS count FROM loan WHERE id_member = ? AND isbn_book = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idMember);
            preparedStatement.setString(2, isbnBook);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    userHasBorrowedBook = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userHasBorrowedBook;
    }
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
    public static void updateUsersState() {
        String sqlQuery = "UPDATE users " +
                "SET state = CASE " +
                "    WHEN block_till IS NOT NULL THEN 'Bloqué' " +
                "    WHEN (" +
                "        SELECT COUNT(*) " +
                "        FROM loan " +
                "        WHERE users.id_user = loan.id_member " +
                "            AND loan_date + INTERVAL loan_duration DAY < CURDATE()" +
                "    ) >= 3 THEN 'Bloqué' " +
                "    ELSE 'Actif' " +
                "END, " +
                "block_till = CASE " +
                "    WHEN (" +
                "        SELECT COUNT(*) " +
                "        FROM loan " +
                "        WHERE users.id_user = loan.id_member " +
                "            AND loan_date + INTERVAL loan_duration DAY < CURDATE()" +
                "    ) >= 3 THEN DATE_ADD(CURDATE(), INTERVAL 90 DAY) " +
                "    ELSE NULL " +
                "END";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String[]> getTopFourBooks() {
        List<String[]> topBooks = new ArrayList<>();
        String sqlQuery = "SELECT isbn_book, COUNT(*) AS loan_count " +
                "FROM loan " +
                "GROUP BY isbn_book " +
                "ORDER BY loan_count DESC " +
                "LIMIT 4";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn_book");
                int loanCount = resultSet.getInt("loan_count");
                topBooks.add(new String[]{isbn, String.valueOf(loanCount)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topBooks;
    }
    public static int getLoanExpiredCount() {
        int expiredCount = 0;
        String sqlQuery = "SELECT COUNT(*) FROM loan WHERE loan_date + INTERVAL loan_duration DAY < CURDATE()";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                expiredCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expiredCount;
    }
    public static int getTotalLoans() {
        int totalLoans = 0;
        String sqlQuery = "SELECT COUNT(*) FROM loan";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                totalLoans = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalLoans;
    }
    public static void deleteLoan(int loanId) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM loan WHERE id_loan = ?")) {
            statement.setInt(1, loanId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
