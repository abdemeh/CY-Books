package org.cybooks;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The Loan class represents a loan of a book by a member.
 */
public class Loan {
    private int id_loan;
    private String book_isbn;
    private int id_member;
    private Date loan_date;
    private int loan_duration;
    private boolean expired;

    /**
     * Constructs a new Loan object with the specified details.
     *
     * @param id_loan       the unique identifier of the loan
     * @param book_isbn     the ISBN of the borrowed book
     * @param id_member     the unique identifier of the borrowing member
     * @param loan_date     the date when the loan was initiated
     * @param loan_duration the duration of the loan in days
     * @param expired       indicates if the loan has expired
     */
    public Loan(int id_loan, String book_isbn, int id_member, Date loan_date, int loan_duration, boolean expired) {
        this.id_loan = id_loan;
        this.book_isbn = book_isbn;
        this.id_member = id_member;
        this.loan_date = loan_date;
        this.loan_duration = loan_duration;
        this.expired = expired;
    }

    /**
     * Returns the unique identifier of the loan.
     *
     * @return the id of the loan
     */
    public int getIdLoan() {
        return id_loan;
    }

    /**
     * Sets the unique identifier of the loan.
     *
     * @param id_loan the id of the loan
     */
    public void setIdLoan(int id_loan) {
        this.id_loan = id_loan;
    }

    /**
     * Returns the ISBN of the borrowed book.
     *
     * @return the ISBN of the book
     */
    public String getBookIsbn() {
        return book_isbn;
    }

    /**
     * Sets the ISBN of the borrowed book.
     *
     * @param book_isbn the ISBN of the book
     */
    public void setBookIsbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    /**
     * Returns the unique identifier of the borrowing member.
     *
     * @return the id of the member
     */
    public int getIdMember() {
        return id_member;
    }

    /**
     * Sets the unique identifier of the borrowing member.
     *
     * @param id_member the id of the member
     */
    public void setIdMember(int id_member) {
        this.id_member = id_member;
    }

    /**
     * Returns the date when the loan was initiated.
     *
     * @return the loan date
     */
    public Date getLoanDate() {
        return loan_date;
    }

    /**
     * Sets the date when the loan was initiated.
     *
     * @param loan_date the loan date
     */
    public void setLoanDate(Date loan_date) {
        this.loan_date = loan_date;
    }

    /**
     * Returns the duration of the loan in days.
     *
     * @return the loan duration
     */
    public int getLoanDuration() {
        return loan_duration;
    }

    /**
     * Sets the duration of the loan in days.
     *
     * @param loan_duration the loan duration
     */
    public void setLoanDuration(int loan_duration) {
        this.loan_duration = loan_duration;
    }

    /**
     * Checks if the loan has expired.
     *
     * @return true if the loan has expired, false otherwise
     */
    public boolean isExpired() {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(loan_date);
        cal.add(Calendar.DAY_OF_MONTH, loan_duration);
        Date dueDate = cal.getTime();
        return currentDate.after(dueDate);
    }

    /**
     * Calculates the remaining days until the loan expires.
     *
     * @return the number of remaining days
     */
    public int getRemainingDays() {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(loan_date);
        cal.add(Calendar.DAY_OF_MONTH, loan_duration);
        Date dueDate = cal.getTime();
        // Calculate the difference in milliseconds
        long diffInMillies = dueDate.getTime() - currentDate.getTime();
        // Convert the difference from milliseconds to days
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
        return (int) diffInDays;
    }

    /**
     * Returns the date when the loan expires.
     *
     * @return the expiration date of the loan
     */
    public Date getExpirationDate() {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(loan_date);
        cal.add(Calendar.DAY_OF_MONTH, loan_duration);
        return cal.getTime();
    }

    /**
     * Sets the expiration status of the loan.
     *
     * @param expired the expiration status of the loan
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    /**
     * Returns a string representation of the Loan object.
     *
     * @return a string representation of the Loan object
     */
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return "Loan{" +
                "book_isbn='" + book_isbn + '\'' +
                ", id_member='" + id_member + '\'' +
                ", loan_date='" + loan_date + '\'' +
                ", loan_duration='" + loan_duration + '\'' +
                ", expired='" + expired + '\'' +
                '}';
    }
}
