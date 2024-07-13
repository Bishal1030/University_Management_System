package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceController {

    @FXML
    private TextField studentIDField;
    @FXML
    private TextField dateField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TextArea outputArea;

    private List<Attendance> attendanceList = new ArrayList<>();
    private static final String ATTENDANCE_DATA_FILE = "attendance.dat";

    @FXML
    private void initialize() {
        statusComboBox.getItems().addAll("Present", "Absent", "Late");
        loadAttendanceFromFile();
    }

    @FXML
    private void handleSaveAttendanceButton() {
        Attendance attendance = getAttendanceFromFields();
        if (attendance == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        Optional<Attendance> existingAttendance = attendanceList.stream()
                .filter(a -> a.getStudentID().equals(attendance.getStudentID()) && a.getDate().equals(attendance.getDate()))
                .findFirst();

        if (existingAttendance.isPresent()) {
            outputArea.appendText("Attendance for this student on this date already exists.\n");
            return;
        }

        attendanceList.add(attendance);
        saveAttendanceToFile();
        outputArea.appendText("Attendance saved: " + attendance.toString() + "\n");
        clearFields();
    }

    @FXML
    private void handleLoadAttendanceButton() {
        loadAttendanceFromFile();
        displayAttendance();
    }

    @FXML
    private void handleUpdateAttendanceButton() {
        Attendance attendance = getAttendanceFromFields();
        if (attendance == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        for (int i = 0; i < attendanceList.size(); i++) {
            if (attendanceList.get(i).getStudentID().equals(attendance.getStudentID()) && attendanceList.get(i).getDate().equals(attendance.getDate())) {
                attendanceList.set(i, attendance);
                saveAttendanceToFile();
                outputArea.appendText("Attendance updated: " + attendance.toString() + "\n");
                clearFields();
                return;
            }
        }

        outputArea.appendText("Attendance not found.\n");
    }

    @FXML
    private void handleDeleteAttendanceButton() {
        String studentID = studentIDField.getText();
        String date = dateField.getText();
        if (studentID.isEmpty() || date.isEmpty()) {
            outputArea.appendText("Please enter student ID and date to delete.\n");
            return;
        }

        attendanceList.removeIf(attendance -> attendance.getStudentID().equals(studentID) && attendance.getDate().equals(date));
        saveAttendanceToFile();
        outputArea.appendText("Attendance deleted for student ID: " + studentID + " on date: " + date + "\n");
        clearFields();
    }

    private Attendance getAttendanceFromFields() {
        String studentID = studentIDField.getText();
        String date = dateField.getText();
        String status = statusComboBox.getValue();

        if (studentID.isEmpty() || date.isEmpty() || status == null) {
            return null;
        }

        return new Attendance(studentID, date, status);
    }

    private void clearFields() {
        studentIDField.clear();
        dateField.clear();
        statusComboBox.getSelectionModel().clearSelection();
    }

    private void saveAttendanceToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ATTENDANCE_DATA_FILE))) {
            oos.writeObject(attendanceList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadAttendanceFromFile() {
        File file = new File(ATTENDANCE_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ATTENDANCE_DATA_FILE))) {
                attendanceList = (List<Attendance>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayAttendance() {
        outputArea.clear();
        for (Attendance attendance : attendanceList) {
            outputArea.appendText(attendance.toString() + "\n");
        }
    }
}
