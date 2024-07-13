package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherController {

    @FXML
    private TextField teacherIDField;

    @FXML
    private TextField teacherNameField;

    @FXML
    private TextArea outputArea;

    private static final String FILE_NAME = "teachers.dat";
    private List<Teacher> teacherList = new ArrayList<>();

    @FXML
    public void initialize() {
        loadTeachersFromFile();
    }

    @FXML
    private void handleSaveTeacherButton() {
        String id = teacherIDField.getText();
        String name = teacherNameField.getText();

        if (id.isEmpty() || name.isEmpty()) {
            outputArea.setText("Please enter both Teacher ID and Name.");
            return;
        }

        Teacher teacher = new Teacher(id, name);
        teacherList.add(teacher);
        saveTeachersToFile();

        teacherIDField.clear();
        teacherNameField.clear();
        outputArea.setText("Teacher saved successfully!");
    }

    @FXML
    private void handleLoadTeachersButton() {
        loadTeachersFromFile();
        outputArea.clear();

        if (teacherList.isEmpty()) {
            outputArea.setText("No teachers found.");
            return;
        }

        for (Teacher teacher : teacherList) {
            outputArea.appendText(teacher.toString() + "\n");
        }
    }

    private void saveTeachersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(teacherList);
        } catch (IOException e) {
            outputArea.setText("Error saving teachers: " + e.getMessage());
        }
    }

    private void loadTeachersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            teacherList = (List<Teacher>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, ignore as it might be the first time the application is run
        } catch (IOException | ClassNotFoundException e) {
            outputArea.setText("Error loading teachers: " + e.getMessage());
        }
    }
}
