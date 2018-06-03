package com.notification.client.controllers;

import java.io.IOException;

import com.notification.client.rest.UserDAOService;
import com.notification.client.components.entities.User;
import com.notification.client.interfaces.LoggerService;
import com.notification.client.services.LoggerServiceImpl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RegisterController {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    @FXML private Button cancelButton;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField loginField;
    @FXML private TextField passwordField;

    private UserDAOService userDAOService;

	public RegisterController() {
        userDAOService = new UserDAOService();
	}
	
	public void showDialog() {
		Stage stage = new Stage();
		BorderPane pane;
		
		try {
			pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/RegisterWindow.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setTitle("Реєстрація");
			stage.show();
			
		} catch(IOException | NullPointerException e) {
			logger.logError(e, "Exception during register form opening");
			throw new RuntimeException(e);
		}
	}
	
	public void register() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String username = loginField.getText();
		String password = passwordField.getText();
		
		if(firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("")) {
			firstNameField.setText("");
			lastNameField.setText("");
			loginField.setText("");
			passwordField.setText("");
			return;			
		}
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setPassword(password);
		
		User returnedUser = userDAOService.createUser(user);

		if (returnedUser == null) {
			return;
		}

		MainController.setUser(returnedUser);
		openMainWindow();
	}
	
	public void cancel() {
		LoginController loginController = new LoginController();
		loginController.showDialog();		
		closeCurrentWindow();
	}
	
	private void openMainWindow() {
		MainController mainController = new MainController();
		mainController.showDialog();		
		closeCurrentWindow();
	}
	
	private void closeCurrentWindow() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
}
