package com.example.project;

import java.io.Serializable;

public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;

    private String staffID;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    public Staff(String staffID, String firstName, String lastName, String email, String username, String password) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
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
        return "StaffID: " + staffID + ", FirstName: " + firstName + ", LastName: " + lastName + ", Email: " + email + ", Username: " + username;
    }
}
