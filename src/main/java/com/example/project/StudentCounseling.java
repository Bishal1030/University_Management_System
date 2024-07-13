package com.example.project;

import java.io.Serializable;

public class StudentCounseling implements Serializable {
    private String studentID;
    private String counselorName;
    private String counselingDate;
    private String notes;

    public StudentCounseling(String studentID, String counselorName, String counselingDate, String notes) {
        this.studentID = studentID;
        this.counselorName = counselorName;
        this.counselingDate = counselingDate;
        this.notes = notes;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public String getCounselingDate() {
        return counselingDate;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "StudentID: " + studentID + ", Counselor: " + counselorName + ", Date: " + counselingDate + ", Notes: " + notes;
    }
}
