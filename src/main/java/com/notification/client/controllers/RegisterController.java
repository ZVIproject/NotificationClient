package com.notification.client.controllers;

import java.io.IOException;
import java.util.logging.Logger;

import com.notification.client.common.dao.UserDAOService;
import com.notification.client.common.entities.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RegisterController {
	
	private static final Logger logger = Logger.getLogger(RegisterController.class.getName());
	
	@FXML private Button cancelButton;
	
	@FXML private TextField firstNameField;
	@FXML private TextField lastNameField;
	@FXML private TextField patronymicField;
	@FXML private TextField loginField;
	@FXML private TextField passwordField;
	@FXML private TextField emailField;

	private UserDAOService userDAOService = new UserDAOService(); 
	
	public void showDialog() {
		Stage stage = new Stage();
		BorderPane pane;
		
		try {
			pane = (BorderPane)FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/RegisterWindow.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setTitle("Реєстрація");
			stage.setResizable(false);
			stage.show();
			
		} catch(IOException | NullPointerException e) {
			logger.info(e.getMessage() + ": " + e.getCause());
			throw new RuntimeException(e);
		}
	}
	
	public void register() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String patronymic = patronymicField.getText();
		String username = loginField.getText();
		String password = passwordField.getText();
		String email = emailField.getText();
		
		if(firstName.equals("") || lastName.equals("") || patronymic.equals("") ||
				username.equals("") || password.equals("") || email.equals("")) {
			firstNameField.setText("");
			lastNameField.setText("");
			patronymicField.setText("");
			loginField.setText("");
			passwordField.setText("");
			emailField.setText("");
			return;			
		}
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPatronymic(patronymic);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		
		userDAOService.create(user);		
	}
	
	public void cancel() {
		LoginController loginController = new LoginController();
		loginController.showDialog();
		
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	
}
