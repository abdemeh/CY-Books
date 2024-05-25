package org.cybooks;

import java.sql.Date;

public class Member extends User {

    public Member(int id, String lastname, String firstname, String email, Date inscriptionDate, String state, Date birthday, String phone, String sex) {
        super(id, lastname, firstname, email, inscriptionDate, state, birthday, phone, sex);
    }

    // Override toString method for convenient printing
    @Override
    public String toString() {
        return "Member{" +
                super.toString() +
                '}';
    }
}
