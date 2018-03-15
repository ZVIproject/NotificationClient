package com.notification.client.controllers;

import com.notification.client.components.entities.Message;
import com.notification.client.rest.MessageDAOService;
import com.notification.client.services.LoggerServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlackListController {

    private LoggerServiceImpl logger;
    private MessageDAOService messageDAOService;
    private Stage stage;

    private ObservableList<Message> activeList = FXCollections.observableArrayList();
    private ObservableList<Message> blackList = FXCollections.observableArrayList();

    @FXML private TableView<Message> activeEmailsTable;
    @FXML private TableView<Message> blackListEmailsTable;
    @FXML private TableColumn<Message, String> activeEmails;
    @FXML private TableColumn<Message, Boolean> blockColumn;
    @FXML private TableColumn<Message, String> blackListedEmails;
    @FXML private TableColumn<Message, Boolean> unlockColumn;

    @FXML public void initialize() {
        setRecordsActive();
        setRecordsBlackList();
    }

    public BlackListController() {
        logger = new LoggerServiceImpl();
        messageDAOService = new MessageDAOService();
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/BlackListWindow.fxml"));
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

    public void save() {
        List<Message> changedActiveList = getChangedActive();
        List<Message> changedBlackList = getChangedBlackListed();

        if (changedActiveList != null && !changedActiveList.isEmpty()) {
            List<String> emails = changedActiveList.stream()
                    .map(message -> message.getEmail())
                    .collect(Collectors.toList());
            messageDAOService.addToBlackList(emails);
            setRecordsActive();
        }

        if (changedBlackList != null && !changedBlackList.isEmpty()) {
            List<String> emails = changedBlackList.stream()
                    .map(message -> message.getEmail())
                    .collect(Collectors.toList());
            messageDAOService.removeFromBlackList(emails);
            setRecordsBlackList();
        }
    }

    public void cancel() {
        closeCurrentWindow();
    }

    private List<Message> getChangedActive() {
        List<Message> changedMessages = new ArrayList<>();
        activeList.forEach(message -> {
            if (!message.getBlackListed()) {
                changedMessages.add(message);
                activeList.remove(message);
            }
        });
        return  changedMessages;
    }

    private List<Message> getChangedBlackListed() {
        List<Message> changedMessages = new ArrayList<>();
        blackList.forEach(message -> {
            if (message.getBlackListed()) {
                changedMessages.add(message);
                blackList.remove(message);
            }
        });
        return changedMessages;
    }

    private void setRecordsActive() {
        List<Message> messages = messageDAOService.getMessages();
        messages.forEach(message -> {
            message.setBlackListed(false);
            activeList.add(message);
        });
        displayRecordsActive();
    }

    private void setRecordsBlackList() {
        List<Message> messages = messageDAOService.getBlackListedMessages();
        messages.forEach(message -> {
            message.setBlackListed(true);
            blackList.add(message);
        });
        displayRecordsBlackList();
    }

    private void displayRecordsActive() {
        activeEmails.setCellValueFactory(new PropertyValueFactory<Message, String>("email"));
        blockColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        blockColumn.setCellValueFactory(new PropertyValueFactory<Message, Boolean>("isBlackListed"));
        activeEmailsTable.setItems(activeList);
    }

    private void displayRecordsBlackList() {
        blackListedEmails.setCellValueFactory(new PropertyValueFactory<Message, String>("email"));
        unlockColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        unlockColumn.setCellValueFactory(new PropertyValueFactory<Message, Boolean>("isBlackListed"));
        blackListEmailsTable.setItems(blackList);
    }

    private void closeCurrentWindow() {
        stage.close();
    }
}
