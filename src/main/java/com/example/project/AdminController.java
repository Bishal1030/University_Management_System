package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminController {

    @FXML
    private TextField adminIDField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextArea outputArea;

    private List<Admin> adminList = new ArrayList<>();
    private static final String ADMIN_DATA_FILE = "admins.dat";
    private static final String STUDENT_CREDENTIALS_DIRECTORY = "student_credentials/";

    @FXML
    private void initialize() {
        loadAdminsFromFile();
    }

    @FXML
    private void handleSaveAdminButton() {
        Admin admin = getAdminFromFields();
        if (admin == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        Optional<Admin> existingAdmin = adminList.stream()
                .filter(a -> a.getAdminID().equals(admin.getAdminID()))
                .findFirst();

        if (existingAdmin.isPresent()) {
            outputArea.appendText("Admin with this ID already exists.\n");
            return;
        }

        adminList.add(admin);
        saveAdminsToFile();
        outputArea.appendText("Admin saved: " + admin.toString() + "\n");
        clearFields();
    }

    @FXML
    private void handleLoadAdminsButton() {
        loadAdminsFromFile();
        displayAdmins();
    }

    @FXML
    private void handleUpdateAdminButton() {
        Admin admin = getAdminFromFields();
        if (admin == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        for (int i = 0; i < adminList.size(); i++) {
            if (adminList.get(i).getAdminID().equals(admin.getAdminID())) {
                adminList.set(i, admin);
                saveAdminsToFile();
                outputArea.appendText("Admin updated: " + admin.toString() + "\n");
                clearFields();
                return;
            }
        }

        outputArea.appendText("Admin not found.\n");
    }

    @FXML
    private void handleDeleteAdminButton() {
        String adminID = adminIDField.getText();
        if (adminID.isEmpty()) {
            outputArea.appendText("Please enter an admin ID to delete.\n");
            return;
        }

        adminList.removeIf(admin -> admin.getAdminID().equals(adminID));
        saveAdminsToFile();
        outputArea.appendText("Admin deleted with ID: " + adminID + "\n");
        clearFields();
    }

    @FXML
    private void handleDeleteStudentWithCredentialsButton() {
        String studentID = adminIDField.getText();
        if (studentID.isEmpty()) {
            outputArea.appendText("Please enter a student ID to delete.\n");
            return;
        }

        Student studentToDelete = StudentService.findStudentByID(studentID);

        if (studentToDelete != null) {
            StudentService.deleteStudent(studentToDelete);
            outputArea.appendText("Student deleted with ID: " + studentID + "\n");

            // Delete credentials file
            StudentService.deleteStudentCredentialsFile(studentToDelete);
        } else {
            outputArea.appendText("Student not found.\n");
        }

        clearFields();
    }

    private Admin getAdminFromFields() {
        String adminID = adminIDField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (adminID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return null;
        }

        String hashedPassword = hashPassword(password);
        return new Admin(adminID, firstName, lastName, email, username, hashedPassword);
    }

    private void clearFields() {
        adminIDField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        usernameField.clear();
        passwordField.clear();
    }

    private void saveAdminsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ADMIN_DATA_FILE))) {
            oos.writeObject(adminList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadAdminsFromFile() {
        File file = new File(ADMIN_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ADMIN_DATA_FILE))) {
                adminList = (List<Admin>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayAdmins() {
        outputArea.clear();
        for (Admin admin : adminList) {
            outputArea.appendText(admin.toString() + "\n");
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
}
