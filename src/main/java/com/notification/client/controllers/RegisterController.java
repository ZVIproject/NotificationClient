package com.notification.client.controllers;

import java.io.IOException;

import com.notification.client.components.entities.Employee;
import com.notification.client.controllers.alerts.EmployeeNotExistsAlertController;
import com.notification.client.rest.UserService;
import com.notification.client.components.entities.User;

import com.notification.client.services.dao.EmployeeDAOServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @FXML private Button cancelButton;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField loginField;
    @FXML private TextField passwordField;

    private EmployeeDAOServiceImpl employeeDAOService;
    private UserService userRemoteService;

	public RegisterController() {
		employeeDAOService = new EmployeeDAOServiceImpl();
		userRemoteService = new UserService();
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
			logger.error("Exception during register form opening", e);
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

		Employee employee = employeeDAOService.getUserByFirstAndLastNames(firstName, lastName);
		if (employee == null) {
			EmployeeNotExistsAlertController alert = new EmployeeNotExistsAlertController();
			alert.showDialog();
			return;
		}

		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setPassword(password);

		User returnedUser = userRemoteService.createUser(user);
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
