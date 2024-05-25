package org.cybooks;

import java.sql.Date;

public class Admin extends User {
    private String password;

    public Admin(int id, String lastname, String firstname, String email, Date inscriptionDate, String state, Date birthday, String phone, String sex, String password) {
        super(id, lastname, firstname, email, inscriptionDate, state, birthday, phone, sex);
        this.password = password;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Override toString method for convenient printing
    @Override
    public String toString() {
        return "Admin{" +
                super.toString() +
                '}';
    }
}
