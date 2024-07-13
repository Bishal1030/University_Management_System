package com.example.project;

import java.io.Serializable;

public class StudentParticipationInSports implements Serializable {
    private String studentID;
    private String sportName;
    private String sportDate;

    public StudentParticipationInSports(String studentID, String sportName, String sportDate) {
        this.studentID = studentID;
        this.sportName = sportName;
        this.sportDate = sportDate;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getSportName() {
        return sportName;
    }

    public String getSportDate() {
        return sportDate;
    }

    @Override
    public String toString() {
        return "StudentID: " + studentID + ", Sport: " + sportName + ", Date: " + sportDate;
    }
}
