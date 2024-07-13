package com.example.project;

import java.io.Serializable;

public class ExtraCurriculum implements Serializable {
    private String studentID;
    private String activity;

    public ExtraCurriculum(String studentID, String activity) {
        this.studentID = studentID;
        this.activity = activity;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getActivity() {
        return activity;
    }

    @Override
    public String toString() {
        return "StudentID: " + studentID + ", Activity: " + activity;
    }
}
