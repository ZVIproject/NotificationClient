package com.notification.client.controllers;

import java.io.IOException;

import com.notification.client.dao.UserDAOService;
import com.notification.client.components.entities.User;
import com.notification.client.interfaces.ILoggerService;
import com.notification.client.services.LoggerService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RegisterController {
	
	private ILoggerService loggerService;
	
	@FXML private Button cancelButton;
	
	@FXML private TextField firstNameField;
	@FXML private TextField lastNameField;
	@FXML private TextField loginField;
	@FXML private TextField passwordField;

	private UserDAOService userDAOService = new UserDAOService(); 
	
	
	public RegisterController() {
		loggerService = new LoggerService();
	}
	
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
			loggerService.logError(e, "Exception during register form opening");
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
		
		Integer id = userDAOService.create(user);
		
		if(id >= 0) {
			user.setId(id);
			MainController.setUser(user);
			openMainWindow();
		} else {
			return;
		}
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
