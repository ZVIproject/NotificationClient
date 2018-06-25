package com.notification.client.controllers;

import com.notification.client.components.entities.Employee;
import com.notification.client.components.entities.User;
import com.notification.client.controllers.alerts.EmployeeNotExistsAlertController;
import com.notification.client.interfaces.dao.EmployeeDAOService;
import com.notification.client.rest.UserService;
import com.notification.client.services.LoggerServiceImpl;
import com.notification.client.services.XLSFileParserImpl;
import com.notification.client.services.dao.EmployeeDAOServiceImpl;
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
import java.util.ArrayList;
import java.util.List;

public class AddUserController {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();
    public static AddUserController currentController;

    private EmployeeDAOService employeeDAOService;
    private UserService userDAOService;
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


    public AddUserController() {
        employeeDAOService = new EmployeeDAOServiceImpl();
        userDAOService = new UserService();
        parser = new XLSFileParserImpl();
        currentController = this;
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/AddUserWindow.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Додати користувачів");
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
                Employee employee = employeeDAOService.getUserByFirstAndLastNames(user.getFirstName(), user.getLastName());
                if (employee == null) {
                    EmployeeNotExistsAlertController alert = new EmployeeNotExistsAlertController();
                    alert.showDialog();
                    continue;
                }

                userDAOService.createUser(user);
                OperationSucceedController controller = new OperationSucceedController();
                controller.showDialog();


            } catch(NullPointerException e) {
                OperationFailedController controller = new OperationFailedController();
                controller.showDialog();
                logger.logError(e, "NullPointerException in readFromFile method");
                throw new RuntimeException(e);
            }
        }
    }

    public void readFromFile() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Ім'я");
        arrayList.add("Прізвище");
        arrayList.add("Ім'я входу");
        arrayList.add("Пароль");
        arrayList.add("Позиція");
        List<List<Cell>> rows = parser.readFile(stage, arrayList);
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

    public void delete() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        observableList.remove(selectedUser);
    }

    private void setColumnValues(List<Cell> cells) {
        User user = User.getUser(cells);
        observableList.add(user);
        displayRecords();
    }

    private void displayRecords() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
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

