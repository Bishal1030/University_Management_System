package com.example.project;

import java.io.Serializable;
import java.time.LocalDate;

public class AttendanceRecord implements Serializable {
    private String studentID;
    private LocalDate date;
    private boolean present;

    public AttendanceRecord(String studentID, LocalDate date, boolean present) {
        this.studentID = studentID;
        this.date = date;
        this.present = present;
    }

    // Getters and setters (if needed)

    @Override
    public String toString() {
        return "Student ID: " + studentID + "\nDate: " + date + "\nPresent: " + (present ? "Yes" : "No");
    }
}
