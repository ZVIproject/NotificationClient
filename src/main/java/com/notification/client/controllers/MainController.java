package com.notification.client.controllers;

import java.util.List;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;

import com.notification.client.components.entities.User;
import com.notification.client.interfaces.LoggerService;
import com.notification.client.interfaces.XLSFileParser;
import com.notification.client.services.LoggerServiceImpl;
import com.notification.client.services.XLSFileParserImpl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController {
	
	private static User user;
	
	private LoggerService loggerService;
	private XLSFileParser parser;
	
	private Stage stage;


	public static void setUser(User user) {
		MainController.user = user;
	}
	
	public static User getUser() {
		return MainController.user;
	}
	
	public MainController() {
		parser = new XLSFileParserImpl();
		loggerService = new LoggerServiceImpl();
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
			loggerService.logError(e, "Exception during form loading");
			throw new RuntimeException(e);
		}
	}	
	
	public void readFromFile() {
		List<List<Cell>> rows = parser.readFile(stage);
		for(List<Cell> cells: rows) {
			User user = User.getUser(cells);

			try {
//				userDAOService.create(user);
			} catch(NullPointerException e) {
				loggerService.logError(e, "NullPointerException in readFromFile method");
				throw new RuntimeException(e);
			}
		}
	}
	
}
