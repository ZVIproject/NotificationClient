package com.notification.client;

import com.notification.client.controllers.LoginController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage arg0) {
		LoginController loginController = new LoginController();
		loginController.showDialog();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
