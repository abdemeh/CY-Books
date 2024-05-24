package org.example.cybooks;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Loan {
    private int id_loan;
    private String book_isbn;
    private int id_member;
    private Date loan_date;
    private int loan_duration;
    boolean expired;
    public Loan(int id_loan, String book_isbn,int id_member, Date loan_date, int loan_duration, boolean expired) {
        this.id_loan = id_loan;
        this.book_isbn = book_isbn;
        this.id_member = id_member;
        this.loan_date = loan_date;
        this.loan_duration = loan_duration;
        this.expired = expired;
    }

    public int getIdLoan() {
        return id_loan;
    }

    public void setIdLloan(int id_loan) {
        this.id_loan = id_loan;
    }

    public String getBookIsbn() {
        return book_isbn;
    }

    public void seBookIsbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public int getIdMember() {
        return id_member;
    }

    public void setIdMember(int id_member) {
        this.id_member = id_member;
    }

    public Date getLoanDate() {
        return loan_date;
    }

    public void setLoanDate(Date loan_date) {
        this.loan_date = loan_date;
    }

    public int getLoanDuration() {
        return loan_duration;
    }

    public void setLoanDuration(int loan_duration) {
        this.loan_duration = loan_duration;
    }

    public boolean getExpired() {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(loan_date);
        cal.add(Calendar.DAY_OF_MONTH, loan_duration);
        Date dueDate = cal.getTime();
        return currentDate.after(dueDate);
    }
    public int getRestant(){
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
    public Date getExpiredDate(){
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(loan_date);
        cal.add(Calendar.DAY_OF_MONTH, loan_duration);
        Date dueDate = cal.getTime();
        return dueDate;
    }
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return "Loan{" +
                "book_isbn='" + book_isbn + '\'' +
                ", id_member='" + String.valueOf(id_member) + '\'' +
                ", loan_date='" + loan_date + '\'' +
                ", loan_duration='" + String.valueOf(loan_duration) + '\'' +
                ", expired='" + expired + '\'' +
                '}';
    }
}
