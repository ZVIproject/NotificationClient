package com.notification.client.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.notification.client.common.entities.User;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController {

	private static final Logger logger = Logger.getLogger(MainController.class.getName());
	
	private static User user;
	
	public void showDialog() {
		Stage stage = new Stage();
		BorderPane pane;
		try {
			pane = (BorderPane)FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/MainWindow.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setTitle("Головна");
			stage.setResizable(false);
			stage.show();
			
		} catch(IOException | NullPointerException e) {
			logger.log(Level.SEVERE, "Exception during form loading\n" + e.getMessage() + "\n" + e.getCause());
			throw new RuntimeException(e);
		}
	}
	
	public static void setUser(User user) {
		MainController.user = user;
	}
	
	public static User getUser() {
		return MainController.user;
	}
	
}
