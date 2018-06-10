package com.notification.client.controllers;

import com.notification.client.components.entities.Message;
import com.notification.client.rest.MessageRemoteService;
import com.notification.client.services.LoggerServiceImpl;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
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
    private MessageRemoteService messageDAOService;
    private Stage stage;

    private ObservableList<Message> activeList = FXCollections.observableArrayList();
    private ObservableList<Message> blackList = FXCollections.observableArrayList();

    @FXML private TableView<Message> activeEmailsTable;
    @FXML private TableView<Message> blackListEmailsTable;
    @FXML private TableColumn<Message, String> activeEmails;
    @FXML private TableColumn<Message, Boolean> blockColumn ;
    @FXML private TableColumn<Message, String> blackListedEmails;
    @FXML private TableColumn<Message, Boolean> unlockColumn;

    @FXML public void initialize() {
        blockColumn.setCellValueFactory(new PropertyValueFactory<>("blockColumn"));
        setRecordsActive();
        setRecordsBlackList();
    }

    public BlackListController() {
        logger = new LoggerServiceImpl();
        messageDAOService = new MessageRemoteService();
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/BlackListWindow.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Головне вікно");
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

        activeEmailsTable.getItems().clear();
        blackListEmailsTable.getItems().clear();
        setRecordsActive();
        setRecordsBlackList();
    }

    public void cancel() {
        closeCurrentWindow();
    }

    @FXML
    public void changeStatusActive(TableColumn.CellEditEvent<?,?> event) {
        TablePosition<?,?> position = event.getTablePosition();
        int row = position.getRow();
        Message selectedMessage = activeEmailsTable.getSelectionModel().getSelectedItem();
        selectedMessage.setBlackListed(true);
    }

    public void changeStatusInactive() {
        Message selectedMessage = blackListEmailsTable.getSelectionModel().getSelectedItem();
        selectedMessage.setBlackListed(true);
    }

    private List<Message> getChangedActive() {
        List<Message> changedMessages = new ArrayList<>();
        for (Message message : activeList) {
            if (message.isBlackListed().get()) {
                changedMessages.add(message);
            }
        }

        activeList.remove(changedMessages);
        activeEmailsTable.getItems().clear();
        activeEmailsTable.setItems(activeList);
        return  changedMessages;
    }

    private List<Message> getChangedBlackListed() {
        List<Message> changedMessages = new ArrayList<>();
        blackList.forEach(message -> {
            if (!message.isBlackListed().getValue().equals(true)) {
                changedMessages.add(message);
            }
        });

        blackList.remove(changedMessages);
        blackListEmailsTable.getItems().clear();
        setRecordsBlackList();
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
        activeEmails.setCellValueFactory(new PropertyValueFactory<>("email"));
        blockColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        blockColumn.setCellValueFactory(cellData -> {
            Message cellValue = cellData.getValue();
            BooleanProperty property = cellValue.blackListedProperty();
            property.addListener((observable, oldValue, newValue) -> {
                cellValue.setBlackListed(newValue);
            });
            return property;
        });
        activeEmailsTable.setItems(activeList);
    }

    private void displayRecordsBlackList() {
        blackListedEmails.setCellValueFactory(new PropertyValueFactory<>("email"));
        unlockColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        unlockColumn.setCellValueFactory(cellData -> {
            Message cellValue = cellData.getValue();
            BooleanProperty property = cellValue.blackListedProperty();
            property.addListener((observable, oldValue, newValue) -> {
                cellValue.setBlackListed(newValue);
            });
            return property;
        });
        blackListEmailsTable.setItems(blackList);
    }

    private void closeCurrentWindow() {
        stage.close();
    }
}
