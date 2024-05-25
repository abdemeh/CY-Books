package org.cybooks;

import java.sql.Date;

/**
 * The User class represents a user in the CyBooks application.
 * Each user has a unique identifier, personal information such as name, email, birthday, phone number,
 * subscription date, and state (e.g., active or inactive).
 */
public class User {
    private int id; // Unique identifier for the user
    private String firstname; // First name of the user
    private String lastname; // Last name of the user
    private String email; // Email address of the user
    private Date inscriptionDate; // Date when the user subscribed to the service
    private String state; // State of the user (e.g., active, inactive)
    private Date birthday; // Birthday of the user
    private String phone; // Phone number of the user
    private String sex; // Gender of the user

    /**
     * Constructs a new User with the specified attributes.
     *
     * @param id              the unique identifier for the user
     * @param lastname        the last name of the user
     * @param firstname       the first name of the user
     * @param email           the email address of the user
     * @param inscriptionDate the date when the user subscribed to the service
     * @param state           the state of the user (e.g., active, inactive)
     * @param birthday        the birthday of the user
     * @param phone           the phone number of the user
     * @param sex             the gender of the user
     */
    public User(int id, String lastname, String firstname, String email, Date inscriptionDate,
                String state, Date birthday, String phone, String sex) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.inscriptionDate = inscriptionDate;
        this.state = state;
        this.birthday = birthday;
        this.phone = phone;
        this.sex = sex;
    }

    // Getters and Setters

    /**
     * Returns the unique identifier of the user.
     *
     * @return the user's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the user's unique identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return firstname;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstname the user's first name
     */
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastname;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastname the user's last name
     */
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the subscription date of the user.
     *
     * @return the user's subscription date
     */
    public Date getInscriptionDate() {
        return inscriptionDate;
    }

    /**
     * Sets the subscription date of the user.
     *
     * @param inscriptionDate the user's subscription date
     */
    public void setInscriptionDate(Date inscriptionDate) {
        this.inscriptionDate = inscriptionDate;
    }

    /**
     * Returns the state of the user.
     *
     * @return the user's state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the user.
     *
     * @param state the user's state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the birthday of the user.
     *
     * @return the user's birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday of the user.
     *
     * @param birthday the user's birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return the user's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phone the user's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the gender of the user.
     *
     * @return the user's gender
     */
    public String getSex() {
        return sex;
    }

    /**
     * Sets the gender of the user.
     *
     * @param sex the user's gender
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", full name='" + lastname + " " + firstname + '\'' +
                ", email='" + email + '\'' +
                ", inscriptionDate=" + inscriptionDate +
                ", state='" + state + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}

