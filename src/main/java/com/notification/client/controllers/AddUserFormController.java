package com.notification.client.controllers;

import com.notification.client.components.entities.User;
import com.notification.client.controllers.alerts.NotEnoughDataAlert;
import com.notification.client.services.LoggerServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddUserFormController {

    private LoggerServiceImpl logger;

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private TextField positionField;
    @FXML private Button cancelButton;

    public AddUserFormController() {
        logger = new LoggerServiceImpl();
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/AddUserForm.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Головне вікно");
            stage.show();
        } catch(IOException | NullPointerException e) {
            logger.logError(e, "Exception during form loading");
            throw new RuntimeException(e);
        }
    }

    public void add() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = nameField.getText();
        String lastName = lastNameField.getText();
        String position = positionField.getText();

        if (username.equals("") || password.equals("") || firstName.equals("")
                || lastName.equals("") || position.equals("")) {
            NotEnoughDataAlert alert = new NotEnoughDataAlert();
            alert.showDialog();
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPosition(position);
        AddUserController.currentController.addUser(user);
        clearFields();
        closeCurrentWindow();
    }

    public void cancel() {
        closeCurrentWindow();
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        nameField.clear();
        lastNameField.clear();
        positionField.clear();
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
