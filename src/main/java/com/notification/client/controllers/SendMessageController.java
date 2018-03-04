package com.notification.client.controllers;

import com.notification.client.services.LoggerServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SendMessageController {

    @FXML
    private TextArea subjectField;

    @FXML
    private TextArea contentField;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<?> receiverTable;

    @FXML
    private TableColumn<?, String> nameColumn;

    @FXML
    private TableColumn<?, String> groupColumn;

    @FXML
    private TableColumn<?, String> emailColumn;

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/SendMessageWindow.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Надіслати повідомлення");
            stage.setResizable(false);
            stage.show();

        } catch(IOException | NullPointerException e) {
            logger.logError(e,  "Exception during form loading");
            throw new RuntimeException(e);
        }
    }

    public void send() {

    }

    public void cancel() {
        closeCurrentWindow();
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

