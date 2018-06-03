package com.notification.client.controllers;

import com.notification.client.services.LoggerServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class OperationSucceedController {

    @FXML private Label findThisLabel;
    @FXML private Button button;

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/OperationSucceed.fxml"));
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
