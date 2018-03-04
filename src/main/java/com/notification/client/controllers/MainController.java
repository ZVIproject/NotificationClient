package com.notification.client.controllers;

import java.io.IOException;
import java.util.List;

import com.notification.client.rest.UserDAOService;
import javafx.event.ActionEvent;

import com.notification.client.components.entities.User;
import com.notification.client.services.LoggerServiceImpl;
import com.notification.client.services.XLSFileParserImpl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;

public class MainController {
	
	private static User user;
	
	private LoggerServiceImpl logger;
	private XLSFileParserImpl parser;
	private Stage stage;

	private UserDAOService userDAOService = new UserDAOService();

	@FXML
    private Button statusButton;


	public static void setUser(User user) {
		MainController.user = user;
	}
	
	public static User getUser() {
		return MainController.user;
	}
	
	public MainController() {
		parser = new XLSFileParserImpl();
		logger = new LoggerServiceImpl();
	}	
	
	public void showDialog() {
		Stage stage = new Stage();
		BorderPane pane;
		try {
			pane = (BorderPane)FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/MainWindow.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setTitle("Головне вікно");
			stage.setResizable(false);
			stage.show();
			this.stage = stage;
		} catch(IOException | NullPointerException e) {
			logger.logError(e, "Exception during form loading");
			throw new RuntimeException(e);
		}
	}	
	
	public void addUser() {
        AddUserController controller = new AddUserController();
        controller.showDialog();
	}

	public void sendMail() {
		SendMessageController controller = new SendMessageController();
		controller.showDialog();
	}

    private void closeCurrentWindow() {
        Stage stage = (Stage) statusButton.getScene().getWindow();
        stage.close();
    }
}
