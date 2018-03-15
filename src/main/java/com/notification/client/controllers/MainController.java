package com.notification.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.notification.client.Main;
import com.notification.client.components.entities.MainStatistic;
import com.notification.client.components.entities.Message;
import com.notification.client.rest.MessageDAOService;
import com.notification.client.rest.UserDAOService;

import com.notification.client.components.entities.User;
import com.notification.client.services.LoggerServiceImpl;
import com.notification.client.services.XLSFileParserImpl;

import com.notification.client.utils.enums.MailStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController {
	
	private static User user;
	
	private LoggerServiceImpl logger;
	private XLSFileParserImpl parser;
	private Stage stage;

	private UserDAOService userDAOService;
	private MessageDAOService messageDAOService;

	private ObservableList<MainStatistic> mainStatistics = FXCollections.observableArrayList();

	@FXML private Label lastDayInField;
	@FXML private Label usernameField;
	@FXML private Button statusButton;
	@FXML private TableView<MainStatistic> statisticTable;
	@FXML private TableColumn<MainStatistic, String> userColumn;
	@FXML private TableColumn<MainStatistic, String> statusColumn;
	@FXML private TableColumn<MainStatistic, String> emailColumn;
	@FXML private TableColumn<MainStatistic, Integer> sendCountColumn;
	@FXML private TableColumn<MainStatistic, String> sentTimeColumn;

	@FXML
    public void initialize() {
	    setRecords();
	    displayRecords();

        lastDayInField.setText(lastDayInField.getText() + " " + user.getModified());
        usernameField.setText(usernameField.getText() + " " + user.getUsername());
    }

	public static void setUser(User user) {
		MainController.user = user;
	}
	
	public static User getUser() {
		return MainController.user;
	}
	
	public MainController() {
		parser = new XLSFileParserImpl();
		logger = new LoggerServiceImpl();
		userDAOService = new UserDAOService();
		messageDAOService = new MessageDAOService();
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

	public void refresh() {
		statisticTable.getItems().clear();
		setRecords();
	    displayRecords();
    }

	public void setRecords() {
	    List<Message> messages = getMostRecent(getMessages());
        for (Message message : messages) {
            MainStatistic statistic = new MainStatistic(
                    message.getUser().getFirstName() + " " + message.getUser().getLastName(),
                    message.getStatus().equals(MailStatus.NEW) ? "Новий" :
                            message.getStatus().equals(MailStatus.FAIL) ? "Помилка при надсилані" : "Надіслано",
                    message.getSendCount(),
                    message.getModified().toString(),
					message.getEmail()
            );
            mainStatistics.add(statistic);
            displayRecords();
        }
    }

    private void displayRecords() {
        userColumn.setCellValueFactory(new PropertyValueFactory<MainStatistic, String>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<MainStatistic, String>("status"));
        sendCountColumn.setCellValueFactory(new PropertyValueFactory<MainStatistic, Integer>("sendCount"));
        sentTimeColumn.setCellValueFactory(new PropertyValueFactory<MainStatistic, String>("created"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<MainStatistic, String>("email"));
        statisticTable.setItems(mainStatistics);
    }

    private List<Message> getMessages() {
        List<Message> messages = messageDAOService.getMessages();
        return messages;
    }

    private List<Message> getMostRecent(List<Message> messages) {
	    return messages.stream()
                .sorted(Comparator.comparing(Message::getModified).reversed())
                .limit(20)
                .collect(Collectors.toList());
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) statusButton.getScene().getWindow();
        stage.close();
    }
}
