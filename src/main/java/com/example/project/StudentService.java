package com.example.project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private static List<Student> studentList = new ArrayList<>();
    private static final String STUDENT_DATA_FILE = "students.dat";
    private static final String STUDENT_CREDENTIALS_DIRECTORY = "student_credentials/";

    static {
        loadStudentsFromFile();
    }

    public static List<Student> getStudentList() {
        return studentList;
    }

    public static void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_DATA_FILE))) {
            oos.writeObject(studentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadStudentsFromFile() {
        File file = new File(STUDENT_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_DATA_FILE))) {
                studentList = (List<Student>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addStudent(Student student) {
        studentList.add(student);
        saveStudentsToFile();
    }

    public static void deleteStudent(Student student) {
        studentList.remove(student);
        saveStudentsToFile();
    }

    public static Student findStudentByID(String studentID) {
        return studentList.stream()
                .filter(student -> student.getStudentID().equals(studentID))
                .findFirst()
                .orElse(null);
    }

    public static void deleteStudentCredentialsFile(Student student) {
        String fileName = STUDENT_CREDENTIALS_DIRECTORY + "student" + student.getStudentID() + ".txt";
        File fileToDelete = new File(fileName);
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("Credentials file deleted: " + fileName);
            } else {
                System.out.println("Failed to delete credentials file: " + fileName);
            }
        } else {
            System.out.println("Credentials file not found: " + fileName);
        }
    }
}
