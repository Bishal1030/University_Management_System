package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private void loadAdminView() {
        loadView("/com/example/project/admin-view.fxml");
    }

    @FXML
    private void loadStaffView() {
        loadView("/com/example/project/staff-view.fxml");
    }

    @FXML
    private void loadStudentView() {
        loadView("/com/example/project/student-view.fxml");
    }

    @FXML
    private void loadTeacherView() {
        loadView("/com/example/project/teacher-view.fxml");
    }

    @FXML
    private void loadAttendanceView() {
        loadView("/com/example/project/attendance-view.fxml");
    }

    @FXML
    private void loadExtraCurriculumView() {
        loadView("/com/example/project/ExtraCurriculum.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
