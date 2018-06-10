package com.notification.client.controllers;

import java.io.IOException;

import com.notification.client.components.entities.User;
import com.notification.client.controllers.alerts.IncorrectUserAlert;
import com.notification.client.rest.UserService;
import com.notification.client.services.LoggerServiceImpl;
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
	
	private static final LoggerServiceImpl logger = new LoggerServiceImpl();
	
	@FXML private Button registerButton;
	@FXML private TextField loginField;	
	@FXML private PasswordField passwordField;

	private UserService userDAOService = new UserService();
	
	public void showDialog() {
		Stage stage = new Stage();
		BorderPane pane;
		try {
			pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/LoginWindow.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setTitle("Вхід");
			stage.show();
			
		} catch(IOException | NullPointerException e) {
			logger.logError(e,  "Exception during form loading");
			throw new RuntimeException(e);
		}
	}
	
	public void login() {
		String login = loginField.getText();
		String password = passwordField.getText();

		if(login.equals("") || password.equals("")) {
			loginField.setText("");
			passwordField.setText("");
			return;
		}

		User user = new User();
		user.setUsername(login);
		user.setPassword(password);

        if ( (user = userDAOService.checkUser(user)) == null) {
            loginField.setText("");
            passwordField.setText("");
            IncorrectUserAlert alert = new IncorrectUserAlert();
            alert.showDialog();
        }

        MainController.setUser(user);
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
