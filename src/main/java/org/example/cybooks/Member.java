package org.example.cybooks;

import java.util.Date;

public class Member {
    private int id;
    private String name;
    private String email;
    private Date inscriptionDate;
    private String state;
    private Date birthday;
    private String phone;
    private String sex;

    public Member(int id,String name, String email, Date inscriptionDate, String state, Date birthday, String phone, String sex) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.inscriptionDate = inscriptionDate;
        this.state = state;
        this.birthday = birthday;
        this.phone = phone;
        this.sex = sex;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getInscriptionDate() {
        return inscriptionDate;
    }

    public void setInscriptionDate(Date inscriptionDate) {
        this.inscriptionDate = inscriptionDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    // Override toString method for convenient printing
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", inscriptionDate=" + inscriptionDate +
                ", state='" + state + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
