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

public class AlertController {

    @FXML private Button button;
    @FXML private Label label;

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    public void showDialog(String message) {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/Alert.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Warning");
            stage.setResizable(false);
            stage.show();

            label.setText(message);

        } catch(IOException | NullPointerException e) {
            logger.logError(e,  "Exception during form loading");
            throw new RuntimeException(e);
        }
    }


}
