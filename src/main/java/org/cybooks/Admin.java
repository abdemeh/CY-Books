package org.cybooks;

import java.sql.Date;

/**
 * Represents an Admin user.
 * Extends the User class.
 */
public class Admin extends User {
    private String password;

    /**
     * Constructs a new Admin with the specified attributes.
     *
     * @param id             the unique identifier of the Admin
     * @param lastname       the last name of the Admin
     * @param firstname      the first name of the Admin
     * @param email          the email address of the Admin
     * @param inscriptionDate the date when the Admin was registered
     * @param state          the state of the Admin
     * @param birthday       the birthday of the Admin
     * @param phone          the phone number of the Admin
     * @param sex            the gender of the Admin
     * @param password       the password of the Admin
     */
    public Admin(int id,
                 String lastname,
                 String firstname,
                 String email,
                 Date inscriptionDate,
                 String state,
                 Date birthday,
                 String phone,
                 String sex,
                 String password) {
        super(id, lastname, firstname, email, inscriptionDate, state, birthday, phone, sex);
        this.password = password;
    }

    /**
     * Gets the password of the Admin.
     *
     * @return the password of the Admin
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the Admin.
     *
     * @param password the new password of the Admin
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the Admin.
     *
     * @return a string representation of the Admin
     */
    @Override
    public String toString() {
        return "Admin{" +
                super.toString() +
                '}';
    }
}
