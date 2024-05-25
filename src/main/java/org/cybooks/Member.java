package org.cybooks;

import java.sql.Date;
import java.util.Calendar;

/**
 * Represents a member user in the system.
 */
public class Member extends User {
    private Date block_till; // User remaining days for unblock

    /**
     * Constructor to initialize a Member object.
     *
     * @param id              The unique identifier of the member.
     * @param lastname        The last name of the member.
     * @param firstname       The first name of the member.
     * @param email           The email address of the member.
     * @param inscriptionDate The date of inscription for the member.
     * @param state           The state of the member's account.
     * @param birthday        The birthday of the member.
     * @param phone           The phone number of the member.
     * @param sex             The gender of the member.
     * @param block_till      The date until which the member is blocked.
     */
    public Member(int id, String lastname, String firstname, String email, Date inscriptionDate, String state, Date birthday, String phone, String sex, Date block_till) {
        super(id, lastname, firstname, email, inscriptionDate, state, birthday, phone, sex);
        this.block_till = block_till;
    }

    /**
     * Gets the remaining days until the member's account is unblocked.
     *
     * @return The remaining days until the member's account is unblocked.
     */
    public int getRemainingDays() {
        java.util.Date currentDate = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(block_till);
        java.util.Date dueDate = cal.getTime();
        // Calculate the difference in milliseconds
        long diffInMillies = dueDate.getTime() - currentDate.getTime();
        // Convert the difference from milliseconds to days
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
        return (int) diffInDays;
    }

    /**
     * Gets the date until which the member is blocked.
     *
     * @return The date until which the member is blocked.
     */
    public Date getBlock_till() {
        return this.block_till;
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
