package com.notification.client.controllers;

import com.notification.client.components.entities.MainStatistic;
import com.notification.client.components.entities.Message;
import com.notification.client.components.entities.User;
import com.notification.client.rest.MessageRemoteService;
import com.notification.client.rest.UserService;
import com.notification.client.services.LoggerServiceImpl;
import com.notification.client.utils.enums.MailStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
	
	private static final LoggerServiceImpl logger = new LoggerServiceImpl();

	private static User user;

	private UserService userDAOService;
	private MessageRemoteService messageDAOService;

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

        statusButton.setStyle("-fx-background-color: #258030");
    }

	public static void setUser(User user) {
		MainController.user = user;
	}
	
	public static User getUser() {
		return MainController.user;
	}
	
	public MainController() {
		userDAOService = new UserService();
		messageDAOService = new MessageRemoteService();
	}	
	
	public void showDialog() {
		Stage stage = new Stage();
		BorderPane pane;
		try {
			pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/MainWindow.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.setTitle("Головне вікно");
			stage.show();
		} catch(IOException | NullPointerException e) {
			logger.logError(e, "Exception during form loading");
			throw new RuntimeException(e);
		}
	}

	public void openStatistic() {
        EmailStatisticController controller = new EmailStatisticController();
        controller.showDialog();
	}

	public void openBlackList() {
		BlackListController blackListController = new BlackListController();
		blackListController.showDialog();
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
	    setRecords();
	    displayRecords();

	    try {
			User user = userDAOService.checkUser(MainController.user);
			statusButton.setStyle("-fx-background-color: #258030");
		} catch (Exception e) {
			statusButton.setStyle("-fx-background-color: #991520");
		}
		statisticTable.getItems().clear();
		setRecords();
	    displayRecords();
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
