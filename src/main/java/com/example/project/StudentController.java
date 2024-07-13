package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentController {

    @FXML
    private TextField studentIDField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField facultyField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextArea outputArea;

    private List<Student> studentList = new ArrayList<>();
    private static final String STUDENT_DATA_FILE = "students.dat";

    @FXML
    private void initialize() {
        loadStudentsFromFile();
    }

    @FXML
    private void handleSaveStudentButton() {
        Student student = getStudentFromFields();
        if (student == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        Optional<Student> existingStudent = studentList.stream()
                .filter(s -> s.getStudentID().equals(student.getStudentID()))
                .findFirst();

        if (existingStudent.isPresent()) {
            outputArea.appendText("Student with this ID already exists.\n");
            return;
        }

        studentList.add(student);
        saveStudentsToFile();
        saveStudentCredentials(student); // Save student credentials to external file
        outputArea.appendText("Student saved: " + student.toString() + "\n");
        clearFields();
    }

    @FXML
    private void handleLoadStudentsButton() {
        loadStudentsFromFile();
        displayStudents();
    }

    @FXML
    private void handleUpdateStudentButton() {
        Student student = getStudentFromFields();
        if (student == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentID().equals(student.getStudentID())) {
                studentList.set(i, student);
                saveStudentsToFile();
                outputArea.appendText("Student updated: " + student.toString() + "\n");
                clearFields();
                return;
            }
        }

        outputArea.appendText("Student not found.\n");
    }

    @FXML
    private void handleDeleteStudentButton() {
        String studentID = studentIDField.getText();
        if (studentID.isEmpty()) {
            outputArea.appendText("Please enter a student ID to delete.\n");
            return;
        }

        studentList.removeIf(student -> student.getStudentID().equals(studentID));
        saveStudentsToFile();
        outputArea.appendText("Student deleted with ID: " + studentID + "\n");
        clearFields();
    }

    private Student getStudentFromFields() {
        String studentID = studentIDField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String faculty = facultyField.getText();
        String email = emailField.getText();
        String gender = genderField.getText();
        String phone = phoneField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (studentID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || faculty.isEmpty() || email.isEmpty() || gender.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return null;
        }

        String hashedPassword = hashPassword(password);
        return new Student(studentID, firstName, lastName, faculty, email, gender, phone, username, hashedPassword);
    }

    private void clearFields() {
        studentIDField.clear();
        firstNameField.clear();
        lastNameField.clear();
        facultyField.clear();
        emailField.clear();
        genderField.clear();
        phoneField.clear();
        usernameField.clear();
        passwordField.clear();
    }

    private void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_DATA_FILE))) {
            oos.writeObject(studentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadStudentsFromFile() {
        File file = new File(STUDENT_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_DATA_FILE))) {
                studentList = (List<Student>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayStudents() {
        outputArea.clear();
        for (Student student : studentList) {
            outputArea.appendText(student.toString() + "\n");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveStudentCredentials(Student student) {
        String fileName = "student" + student.getStudentID() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Username: " + student.getUsername() + "\n");
            writer.write("Password: " + hashPassword(student.getPassword()) + "\n"); // Use hashPassword method here
            File file = new File(fileName);
            String absolutePath = file.getAbsolutePath();
            outputArea.appendText("Student credentials saved to file: " + absolutePath + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
