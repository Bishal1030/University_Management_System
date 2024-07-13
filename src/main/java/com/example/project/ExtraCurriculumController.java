package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExtraCurriculumController {

    @FXML
    private VBox mainVBox;

    @FXML
    private TextArea outputArea;

    @FXML
    private TextField studentIDField;
    @FXML
    private TextField activityField;
    @FXML
    private TextField counselorNameField;
    @FXML
    private TextField counselingDateField;
    @FXML
    private TextArea counselingNotesArea;
    @FXML
    private TextField reportTitleField;
    @FXML
    private TextArea reportContentArea;
    @FXML
    private TextField sportNameField;
    @FXML
    private TextField sportDateField;

    private List<ExtraCurriculum> extraCurriculumList = new ArrayList<>();
    private static final String EXTRA_CURRICULUM_DATA_FILE = "extraCurriculum.dat";

    private List<StudentCounseling> studentCounselingList = new ArrayList<>();
    private static final String STUDENT_COUNSELING_DATA_FILE = "studentCounseling.dat";

    private List<StudentReport> studentReportList = new ArrayList<>();
    private static final String STUDENT_REPORT_DATA_FILE = "studentReport.dat";

    private List<StudentParticipationInSports> studentSportsList = new ArrayList<>();
    private static final String STUDENT_SPORTS_DATA_FILE = "studentSports.dat";

    @FXML
    private void initialize() {
        loadExtraCurriculumFromFile();
        loadCounselingFromFile();
        loadReportFromFile();
        loadSportFromFile();
    }

    @FXML
    private void handleOpenStudentCounselingForm() {
        loadForm("/com/example/project/StudentCounselingForm.fxml");
    }

    @FXML
    private void handleOpenStudentReportForm() {
        loadForm("/com/example/project/StudentReportForm.fxml");
    }

    @FXML
    private void handleOpenStudentParticipationForm() {
        loadForm("/com/example/project/StudentParticipationForm.fxml");
    }

    private void loadForm(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node node = loader.load();
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveExtraCurriculumButton() {
        ExtraCurriculum extraCurriculum = getExtraCurriculumFromFields();
        if (extraCurriculum == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        Optional<ExtraCurriculum> existingExtraCurriculum = extraCurriculumList.stream()
                .filter(ec -> ec.getStudentID().equals(extraCurriculum.getStudentID()) && ec.getActivity().equals(extraCurriculum.getActivity()))
                .findFirst();

        if (existingExtraCurriculum.isPresent()) {
            outputArea.appendText("Extra-curricular activity already recorded for this student.\n");
            return;
        }

        extraCurriculumList.add(extraCurriculum);
        saveExtraCurriculumToFile();
        outputArea.appendText("Extra-curricular activity saved: " + extraCurriculum.toString() + "\n");
        clearExtraCurriculumFields();
    }

    @FXML
    private void handleSaveCounselingButton() {
        StudentCounseling counseling = getCounselingFromFields();
        if (counseling == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        studentCounselingList.add(counseling);
        saveCounselingToFile();
        outputArea.appendText("Counseling session saved: " + counseling.toString() + "\n");
        clearCounselingFields();
    }

    @FXML
    private void handleSaveReportButton() {
        StudentReport report = getReportFromFields();
        if (report == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        studentReportList.add(report);
        saveReportToFile();
        outputArea.appendText("Report saved: " + report.toString() + "\n");
        clearReportFields();
    }

    @FXML
    private void handleSaveSportButton() {
        StudentParticipationInSports sport = getSportFromFields();
        if (sport == null) {
            outputArea.appendText("Please fill in all fields.\n");
            return;
        }

        studentSportsList.add(sport);
        saveSportToFile();
        outputArea.appendText("Sport participation saved: " + sport.toString() + "\n");
        clearSportFields();
    }

    private ExtraCurriculum getExtraCurriculumFromFields() {
        String studentID = studentIDField.getText();
        String activity = activityField.getText();

        if (studentID.isEmpty() || activity.isEmpty()) {
            return null;
        }

        return new ExtraCurriculum(studentID, activity);
    }

    private StudentCounseling getCounselingFromFields() {
        String studentID = studentIDField.getText();
        String counselorName = counselorNameField.getText();
        String counselingDate = counselingDateField.getText();
        String notes = counselingNotesArea.getText();

        if (studentID.isEmpty() || counselorName.isEmpty() || counselingDate.isEmpty() || notes.isEmpty()) {
            return null;
        }

        return new StudentCounseling(studentID, counselorName, counselingDate, notes);
    }

    private StudentReport getReportFromFields() {
        String studentID = studentIDField.getText();
        String title = reportTitleField.getText();
        String content = reportContentArea.getText();

        if (studentID.isEmpty() || title.isEmpty() || content.isEmpty()) {
            return null;
        }

        return new StudentReport(studentID, title, content);
    }

    private StudentParticipationInSports getSportFromFields() {
        String studentID = studentIDField.getText();
        String sportName = sportNameField.getText();
        String sportDate = sportDateField.getText();

        if (studentID.isEmpty() || sportName.isEmpty() || sportDate.isEmpty()) {
            return null;
        }

        return new StudentParticipationInSports(studentID, sportName, sportDate);
    }

    private void clearExtraCurriculumFields() {
        studentIDField.clear();
        activityField.clear();
    }

    private void clearCounselingFields() {
        studentIDField.clear();
        counselorNameField.clear();
        counselingDateField.clear();
        counselingNotesArea.clear();
    }

    private void clearReportFields() {
        studentIDField.clear();
        reportTitleField.clear();
        reportContentArea.clear();
    }

    private void clearSportFields() {
        studentIDField.clear();
        sportNameField.clear();
        sportDateField.clear();
    }

    private void saveExtraCurriculumToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXTRA_CURRICULUM_DATA_FILE))) {
            oos.writeObject(extraCurriculumList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCounselingToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_COUNSELING_DATA_FILE))) {
            oos.writeObject(studentCounselingList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveReportToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_REPORT_DATA_FILE))) {
            oos.writeObject(studentReportList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSportToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENT_SPORTS_DATA_FILE))) {
            oos.writeObject(studentSportsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadExtraCurriculumFromFile() {
        File file = new File(EXTRA_CURRICULUM_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXTRA_CURRICULUM_DATA_FILE))) {
                extraCurriculumList = (List<ExtraCurriculum>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadCounselingFromFile() {
        File file = new File(STUDENT_COUNSELING_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_COUNSELING_DATA_FILE))) {
                studentCounselingList = (List<StudentCounseling>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadReportFromFile() {
        File file = new File(STUDENT_REPORT_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_REPORT_DATA_FILE))) {
                studentReportList = (List<StudentReport>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadSportFromFile() {
        File file = new File(STUDENT_SPORTS_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENT_SPORTS_DATA_FILE))) {
                studentSportsList = (List<StudentParticipationInSports>)
                        ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
