package com.notification.client.controllers;

import com.notification.client.components.entities.Message;
import com.notification.client.components.entities.Statistic;
import com.notification.client.components.entities.User;
import com.notification.client.rest.MessageDAOService;
import com.notification.client.rest.StatisticDAOService;
import com.notification.client.rest.UserDAOService;
import com.notification.client.services.LoggerServiceImpl;
import com.notification.client.services.XLSFileParserImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class EmailStatisticController {

    public static Date from = new Date(System.currentTimeMillis());
    public static Date to = new Date(System.currentTimeMillis());
    public static EmailStatisticController emailStatisticController;

    private LoggerServiceImpl logger;
    private MessageDAOService messageDAOService;
    private Stage stage;

    private ObservableList<Message> observableList = FXCollections.observableArrayList();

    @FXML private TableView<Message> emails;
    @FXML private TableColumn<Message, String> emailColumn;
    @FXML private TableColumn<Message, Integer> sendCountColumn;

    @FXML public void initialize() {
        selectTopActive();
    }

    public EmailStatisticController() {
        logger = new LoggerServiceImpl();
        messageDAOService = new MessageDAOService();
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/EmailStatisticWindow.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Додати користувачів");
            stage.setResizable(false);
            stage.show();
            this.stage = stage;
            emailStatisticController = this;

        } catch(IOException | NullPointerException e) {
            logger.logError(e,  "Exception during form loading");
            throw new RuntimeException(e);
        }
    }

    public void setList(List<Message> messages) {
        observableList = FXCollections.observableArrayList(messages);
    }

    public void selectTopActive() {
        List<Message> messages = messageDAOService.top();
        messages.forEach(message -> {
            observableList.add(message);
        });
        displayRecords();
    }

    public void openTimePerioudForm() {
        DateFilterController controller = new DateFilterController();
        controller.showDialog();
    }

    public void cancel() {
        closeCurrentWindow();
    }

    private void displayRecords() {
        emailColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("email"));
        sendCountColumn.setCellValueFactory(new PropertyValueFactory<Message, Integer>("sendCount"));
        emails.setItems(observableList);
    }

    private void closeCurrentWindow() {
        stage.close();
    }
}

