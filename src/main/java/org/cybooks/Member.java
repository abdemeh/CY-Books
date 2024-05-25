package org.cybooks;

import java.sql.Date;

/**
 * Represents a member user in the system.
 */
public class Member extends User {

    /**
     * Constructor to initialize a Member object.
     *
     * @param id             The unique identifier of the member.
     * @param lastname       The last name of the member.
     * @param firstname      The first name of the member.
     * @param email          The email address of the member.
     * @param inscriptionDate The date of inscription for the member.
     * @param state          The state of the member's account.
     * @param birthday       The birthday of the member.
     * @param phone          The phone number of the member.
     * @param sex            The gender of the member.
     */
    public Member(int id, String lastname, String firstname, String email, Date inscriptionDate, String state, Date birthday, String phone, String sex) {
        super(id, lastname, firstname, email, inscriptionDate, state, birthday, phone, sex);
    }

    /**
     * Overrides the toString method to provide a string representation of the Member object.
     *
     * @return A string representation of the Member object.
     */
    @Override
    public String toString() {
        return "Member{" +
                super.toString() +
                '}';
    }
}
