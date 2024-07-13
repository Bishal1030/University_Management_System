package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaffController {

    @FXML
    private TextField staffIDField;
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

    private List<Staff> staffList = new ArrayList<>();
    private static final String STAFF_DATA_FILE = "staff.dat";

    @FXML
    private void initialize() {
        loadStaffFromFile();
    }

    @FXML
    private void handleSaveStaffButton() {
        Staff staff = getStaffFromFields();
        if (staff == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        Optional<Staff> existingStaff = staffList.stream()
                .filter(s -> s.getStaffID().equals(staff.getStaffID()))
                .findFirst();

        if (existingStaff.isPresent()) {
            outputArea.appendText("Staff with this ID already exists.\n");
            return;
        }

        staffList.add(staff);
        saveStaffToFile();
        outputArea.appendText("Staff saved: " + staff.toString() + "\n");
        clearFields();
    }

    @FXML
    private void handleLoadStaffButton() {
        loadStaffFromFile();
        displayStaff();
    }

    @FXML
    private void handleUpdateStaffButton() {
        Staff staff = getStaffFromFields();
        if (staff == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getStaffID().equals(staff.getStaffID())) {
                staffList.set(i, staff);
                saveStaffToFile();
                outputArea.appendText("Staff updated: " + staff.toString() + "\n");
                clearFields();
                return;
            }
        }

        outputArea.appendText("Staff not found.\n");
    }

    @FXML
    private void handleDeleteStaffButton() {
        String staffID = staffIDField.getText();
        if (staffID.isEmpty()) {
            outputArea.appendText("Please enter a staff ID to delete.\n");
            return;
        }

        staffList.removeIf(staff -> staff.getStaffID().equals(staffID));
        saveStaffToFile();
        outputArea.appendText("Staff deleted with ID: " + staffID + "\n");
        clearFields();
    }

    private Staff getStaffFromFields() {
        String staffID = staffIDField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (staffID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return null;
        }

        String hashedPassword = hashPassword(password);
        return new Staff(staffID, firstName, lastName, email, username, hashedPassword);
    }

    private void clearFields() {
        staffIDField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        usernameField.clear();
        passwordField.clear();
    }

    private void saveStaffToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STAFF_DATA_FILE))) {
            oos.writeObject(staffList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadStaffFromFile() {
        File file = new File(STAFF_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STAFF_DATA_FILE))) {
                staffList = (List<Staff>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayStaff() {
        outputArea.clear();
        for (Staff staff : staffList) {
            outputArea.appendText(staff.toString() + "\n");
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
