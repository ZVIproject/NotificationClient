package com.notification.client.controllers.alerts;

import com.notification.client.services.LoggerServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeNotExistsAlertController {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    @FXML private Button button;

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/EmployeeNotExistsAlert.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Warning");
            stage.show();

        } catch(IOException | NullPointerException e) {
            logger.logError(e,  "Exception during form loading");
            throw new RuntimeException(e);
        }
    }

    public void exit() {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
