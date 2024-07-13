package com.example.project;

import java.io.Serializable;

public class Attendance implements Serializable {
    private String studentID;
    private String date;
    private String status;

    public Attendance(String studentID, String date, String status) {
        this.studentID = studentID;
        this.date = date;
        this.status = status;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "studentID='" + studentID + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
