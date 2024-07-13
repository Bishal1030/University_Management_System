package com.example.project;

import java.io.Serializable;

public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String adminID;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    public Admin(String adminID, String firstName, String lastName, String email, String username, String password) {
        this.adminID = adminID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminID: " + adminID + ", FirstName: " + firstName + ", LastName: " + lastName + ", Email: " + email + ", Username: " + username;
    }
}
