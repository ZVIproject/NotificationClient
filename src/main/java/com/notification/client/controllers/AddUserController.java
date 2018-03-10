package com.notification.client.controllers;

import com.notification.client.components.entities.User;
import com.notification.client.rest.UserDAOService;
import com.notification.client.services.LoggerServiceImpl;
import com.notification.client.services.XLSFileParserImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.util.List;

public class AddUserController {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();
    public static AddUserController currentController;

    private UserDAOService userDAOService;
    private XLSFileParserImpl parser;
    private Stage stage;

    private ObservableList<User> observableList = FXCollections.observableArrayList();

    @FXML private Button cancelButton;
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> lastNameColumn;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, String> passwordColumn;
    @FXML private TableColumn<User, String> positionColumn;
    @FXML private MenuItem addFromForm;


    public AddUserController() {
        userDAOService = new UserDAOService();
        parser = new XLSFileParserImpl();
        currentController = this;
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/AddUserWindow.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Додати користувачів");
            stage.setResizable(false);
            stage.show();
            this.stage = stage;

        } catch(IOException | NullPointerException e) {
            logger.logError(e,  "Exception during form loading");
            throw new RuntimeException(e);
        }
    }

    public void send() {
        for (User user : observableList) {
            try {
                userDAOService.createUser(user);
            } catch(NullPointerException e) {
                logger.logError(e, "NullPointerException in readFromFile method");
                throw new RuntimeException(e);
            }
        }
    }

    public void readFromFile() {
        List<List<Cell>> rows = parser.readFile(stage);
        for(List<Cell> cells: rows) {
            setColumnValues(cells);
        }
    }

    public void usingForm() {
        AddUserFormController formController = new AddUserFormController();
        formController.showDialog();
    }

    public void cancel() {
        closeCurrentWindow();
    }

    private void setColumnValues(List<Cell> cells) {
        User user = User.getUser(cells);
        observableList.add(user);
        displayRecords();
    }

    private void displayRecords() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<User, String>("position"));
        usersTable.setItems(observableList);
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void addUser(User user) {
        observableList.add(user);
        displayRecords();
    }
}

