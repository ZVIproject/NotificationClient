package com.notification.client.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.notification.client.dao.UserDAOService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class.getName());
	
	@FXML private Button registerButton;
	
	@FXML private TextField loginField;	
	@FXML private PasswordField passwordField;
	
	private UserDAOService userDAOService = new UserDAOService();
	
	public void showDialog() {
		Stage stage = new Stage();
		BorderPane pane;
		try {
			pane = (BorderPane)FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/LoginWindow.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setTitle("Вхід");
			stage.setResizable(false);
			stage.show();
			
		} catch(IOException | NullPointerException e) {
			logger.log(Level.SEVERE, "Exception during form loading\n" + e.getMessage() + "\n" + e.getCause());
			throw new RuntimeException(e);
		}
	}
	
	public void login(ActionEvent actionEvent) {		
		String login = loginField.getText();
		String password = passwordField.getText();
		
		if(login.equals("") || password.equals("")) {
			loginField.setText("");
			passwordField.setText("");
			return;
		}
		
//		User user = userDAOService.getUser(login, password);
//		MainController.setUser(user);
		
		openMainWindow();
	}
	
	public void register() {
		RegisterController registerController = new RegisterController();
		registerController.showDialog();
		closeCurrentWindow();
	}
	
	private void openMainWindow() {
		MainController mainController = new MainController();
		mainController.showDialog();
		closeCurrentWindow();
	}
	
	private void closeCurrentWindow() {
		Stage stage = (Stage) registerButton.getScene().getWindow();
		stage.close();
	}
	
}
