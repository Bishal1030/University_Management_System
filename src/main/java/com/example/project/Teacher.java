package com.example.project;

import java.io.Serializable;

public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public Teacher(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher ID: " + id + ", Name: " + name;
    }
}
