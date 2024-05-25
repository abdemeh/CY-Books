package org.cybooks;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Loan entities.
 * Handles database operations related to Loan entities.
 */
public class LoanDAO {

    static final int loanDuration = 15;
    static final int maximumBookExamples = 5;

    /**
     * Adds a new loan record to the database.
     *
     * @param isbnBook the ISBN of the book to be borrowed
     * @param idMember the ID of the member borrowing the book
     * @return a Result object containing the success status and a message
     */
    public static Result addNewLoan(String isbnBook, int idMember) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Result res = new Result(false, "Une erreur est survenue.");

        if (verifyIfUserHaveBorrowedBook(isbnBook, idMember)) {
            res = new Result(false, "L'utilisateur a déjà emprunté ce livre.");
        } else if (verifyMaximumBookExamples(isbnBook)) {
            res = new Result(false, "Nombre maximum d'exemplaires de livre déjà empruntés.");
        } else {
            String insertLoanSQL = "INSERT INTO loan (isbn_book, id_member, loan_date, loan_duration) VALUES (?, ?, ?, ?)";

            try (Connection connection = Database.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertLoanSQL)) {

                preparedStatement.setString(1, isbnBook);
                preparedStatement.setInt(2, idMember);
                preparedStatement.setDate(3, Date.valueOf(today));
                preparedStatement.setInt(4, loanDuration);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    res = new Result(true, "Le prêt a été ajouté avec succès.");
                }
            } catch (SQLException e) {
                res = new Result(false, "Une erreur est survenue : " + e.getMessage());
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * Checks if a user has already borrowed a specific book.
     *
     * @param isbnBook the ISBN of the book
     * @param idMember the ID of the member
     * @return true if the user has borrowed the book, false otherwise
     */
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

    /**
     * Checks if the maximum number of book examples has already been borrowed.
     *
     * @param isbnBook the ISBN of the book
     * @return true if the maximum number has been borrowed, false otherwise
     */
    public static boolean verifyMaximumBookExamples(String isbnBook) {
        boolean verifyMaximumBookExamples = false;

        String query = "SELECT COUNT(*) AS count FROM loan WHERE isbn_book = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, isbnBook);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    verifyMaximumBookExamples = count >= maximumBookExamples;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verifyMaximumBookExamples;
    }

    /**
     * Retrieves the list of all loans.
     *
     * @return the list of all loans
     */
    public static List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();

        String query = "SELECT * FROM loan";
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id_loan = resultSet.getInt("id_loan");
                String isbnBook = resultSet.getString("isbn_book");
                int idMember = resultSet.getInt("id_member");
                Date loanDate = resultSet.getDate("loan_date");
                int loanDuration = resultSet.getInt("loan_duration");
                boolean expired = resultSet.getBoolean("expired");

                Loan loan = new Loan(id_loan, isbnBook, idMember, loanDate, loanDuration, expired);
                loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    /**
     * Retrieves a list of loans for a given user.
     *
     * @param idUser the ID of the user
     * @return a list of loans associated with the user
     */
    public static List<Loan> getLoans(int idUser) {
        List<Loan> borrowedLoans = new ArrayList<>();
        String query = "SELECT id_loan, isbn_book, id_member, loan_date, loan_duration FROM loan WHERE id_member=? ORDER BY DATE_ADD(loan_date, INTERVAL loan_duration DAY) ASC";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idLoan = resultSet.getInt("id_loan");
                String isbnBook = resultSet.getString("isbn_book");
                int idMember = resultSet.getInt("id_member");
                Date loanDate = resultSet.getDate("loan_date");
                int loanDuration = resultSet.getInt("loan_duration");
                boolean expired = new Loan(idLoan, isbnBook, idMember, loanDate, loanDuration, false).isExpired();
                Loan loan = new Loan(idLoan, isbnBook, idMember, loanDate, loanDuration, expired);
                borrowedLoans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowedLoans;
    }

    /**
     * Updates the state of users based on their loan status.
     */
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

    /**
     * Retrieves information about the top four most borrowed books.
     *
     * @return a list of string arrays containing ISBN and loan count of the top four books
     */
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

    /**
     * Retrieves the count of loans that have expired.
     *
     * @return the count of expired loans
     */
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

    /**
     * Retrieves the total count of loans in the database.
     *
     * @return the total count of loans
     */
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

    /**
     * Deletes a loan record from the database.
     *
     * @param loanId the ID of the loan to be deleted
     */
    public static void deleteLoan(int loanId) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM loan WHERE id_loan = ?")) {
            statement.setInt(1, loanId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renews the loan by extending its duration.
     *
     * @param loanId the ID of the loan to be renewed
     */
    public static void renewLoan(int loanId) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE loan SET loan_duration = loan_duration + ? WHERE id_loan = ?")) {
            statement.setInt(1, loanDuration);
            statement.setInt(2, loanId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

