package com.example.project;

import java.io.Serializable;

public class StudentReport implements Serializable {
    private String studentID;
    private String title;
    private String content;

    public StudentReport(String studentID, String title, String content) {
        this.studentID = studentID;
        this.title = title;
        this.content = content;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "StudentID: " + studentID + ", Title: " + title + ", Content: " + content;
    }
}
