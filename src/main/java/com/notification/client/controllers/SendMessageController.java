package com.notification.client.controllers;

import com.notification.client.components.entities.Receiver;
import com.notification.client.components.entities.SendMailDto;
import com.notification.client.rest.SendMessageDAOService;
import com.notification.client.services.LoggerServiceImpl;
import com.notification.client.services.XLSFileParserImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.util.List;

public class SendMessageController {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    public static SendMessageController currentController;

    private SendMessageDAOService sendMessageDAOService;
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

    public SendMessageController() {
        sendMessageDAOService = new SendMessageDAOService();
        parser = new XLSFileParserImpl();
        currentController = this;
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/SendMessageWindow.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Надіслати повідомлення");
            stage.setResizable(false);
            stage.show();
            this.stage = stage;

        } catch(IOException | NullPointerException e) {
            logger.logError(e,  "Exception during form loading");
            throw new RuntimeException(e);
        }
    }

    public void send() {
        String subject = subjectField.getText();
        String content = subjectField.getText();
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

    private String[] getReceiversEmail() {
        String[] emailArray = new String[]{};
        int i = 0;
        for (Receiver receiver : observableList) {
            emailArray[i] = receiver.getEmail();
        }
        return emailArray;
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<Receiver, String>("name"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Receiver, String>("group"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Receiver, String>("email"));
        receiverTable.setItems(observableList);
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

