package com.notification.client.controllers;

import com.notification.client.components.entities.Receiver;
import com.notification.client.components.entities.User;
import com.notification.client.services.LoggerServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddReceiverFormController {

    private LoggerServiceImpl logger;

    @FXML private TextField nameField;
    @FXML private TextField groupField;
    @FXML private TextField emailField;
    @FXML private Button okButton;
    @FXML private Button cancelButton;

    public AddReceiverFormController() {
        logger = new LoggerServiceImpl();
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/AddReceiverForm.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Головне вікно");
            stage.setResizable(false);
            stage.show();
        } catch(IOException | NullPointerException e) {
            logger.logError(e, "Exception during form loading");
            throw new RuntimeException(e);
        }
    }

    public void add() {
        String name = nameField.getText();
        String group = groupField.getText();
        String email = emailField.getText();

        if (name.equals("") || group.equals("") || email.equals("")) {
            return;
        }

        Receiver receiver = new Receiver(name, group, email);
        SendMessageController.currentController.addReceiver(receiver);
        clearFields();
    }

    public void cancel() {
        closeCurrentWindow();
    }

    private void clearFields() {
        nameField.clear();
        groupField.clear();
        emailField.clear();
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
