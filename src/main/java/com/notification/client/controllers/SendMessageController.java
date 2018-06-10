package com.notification.client.controllers;

import com.notification.client.components.entities.Receiver;
import com.notification.client.components.entities.SendMailDto;
import com.notification.client.controllers.alerts.IncorrectDataAlert;
import com.notification.client.rest.SendMessageRemoteService;
import com.notification.client.services.LoggerServiceImpl;
import com.notification.client.services.XLSFileParserImpl;
import com.notification.client.services.dao.StudentsDAOServiceImpl;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendMessageController {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    public static SendMessageController currentController;

    private SendMessageRemoteService sendMessageDAOService;
    private StudentsDAOServiceImpl studentsDAOService;
    private XLSFileParserImpl parser;
    private Stage stage;

    private ObservableList<Receiver> observableList = FXCollections.observableArrayList();

    @FXML private TextArea subjectField;
    @FXML private TextArea contentField;
    @FXML private Button cancelButton;
    @FXML private TableView<Receiver> receiverTable;
    @FXML private TableColumn<Receiver, String> nameColumn;
    @FXML private TableColumn<Receiver, String> groupColumn;
    @FXML private TableColumn<Receiver, String> emailColumn;
    @FXML private TableColumn<Receiver, Boolean> sendColumn;

    @FXML public void initialize() {
        sendColumn.setCellValueFactory(new PropertyValueFactory<>("sendColumn"));
        observableList.addAll(studentsDAOService.getStudentsOfGivenTeacher(MainController.getUser()));
        displayRecords();
    }

    public SendMessageController() {
        sendMessageDAOService = new SendMessageRemoteService();
        studentsDAOService = new StudentsDAOServiceImpl();
        parser = new XLSFileParserImpl();
        currentController = this;
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/SendMessageWindow.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Надіслати повідомлення");
            stage.show();
            this.stage = stage;

        } catch(IOException | NullPointerException e) {
            logger.logError(e,  "Exception during form loading");
            throw new RuntimeException(e);
        }
    }

    public void send() {
        String subject = subjectField.getText();
        String content = contentField.getText();
        if (subject.equals("") || subject.isEmpty() || content.equals("") || content.isEmpty() || observableList.isEmpty()) {
            IncorrectDataAlert alert = new IncorrectDataAlert();
            alert.showDialog();
            return;
        }

        SendMailDto mail = new SendMailDto();
        mail.setSubject(subject);
        mail.setText(content);
        mail.setUserId(MainController.getUser().getId());
        mail.setTo(getReceiversEmail());
        sendMessageDAOService.sendSimpleMessage(mail, "gmail");
        OperationSucceedController controller = new OperationSucceedController();
        controller.showDialog();
    }

    public void readFromFile() {
        List<List<Cell>> rows = parser.readFile(stage);
        for (List<Cell> row : rows) {
            SendMailDto mail = SendMailDto.getMail(row);
            this.subjectField.setText(mail.getSubject());
            this.contentField.setText(mail.getText());
        }
    }

    public void readFromFileReceivers() {
        List<List<Cell>> rows = parser.readFile(stage);
        for (List<Cell> row : rows) {
            setColumnValues(row);
        }
    }

    public void openReceiverForm() {
        AddReceiverFormController controller = new AddReceiverFormController();
        controller.showDialog();
    }

    public void cancel() {
        closeCurrentWindow();
    }

    public void addReceiver(Receiver receiver) {
        observableList.add(receiver);
        displayRecords();
    }

    public void remove() {
        Receiver selectedReceiver = receiverTable.getSelectionModel().getSelectedItem();
        observableList.remove(selectedReceiver);
    }

    private String[] getReceiversEmail() {
        List<String> emailArray = new ArrayList<>();

        observableList.forEach(message -> {
            if (message.isSendProperty().getValue()) {
                emailArray.add(message.getEmail());
            }
        });

        String[] e = new String[]{};
        return emailArray.toArray(e);
    }

    private void setColumnValues(List<Cell> cells) {
        Receiver receiver = new Receiver(
                cells.get(0).getStringCellValue(),
                cells.get(1).getStringCellValue(),
                cells.get(2).getStringCellValue()
        );
        observableList.add(receiver);
        displayRecords();
    }

    private void displayRecords() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        sendColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        sendColumn.setCellValueFactory(cellData -> {
            Receiver cellValue = cellData.getValue();
            BooleanProperty property = cellValue.isSendProperty();
            property.addListener((observable, oldValue, newValue) -> {
                cellValue.setIsSend(newValue);
            });
            return property;
        });
        receiverTable.setItems(observableList);
    }

    @FXML
    public void changeStatus(TableColumn.CellEditEvent<?,?> event) {
        TablePosition<?,?> position = event.getTablePosition();
        int row = position.getRow();
        Receiver selectedItem = receiverTable.getSelectionModel().getSelectedItem();
        selectedItem.setIsSend(true);
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

