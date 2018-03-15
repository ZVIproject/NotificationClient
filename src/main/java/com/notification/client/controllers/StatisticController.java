package com.notification.client.controllers;

import com.notification.client.components.entities.Statistic;
import com.notification.client.components.entities.User;
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
import java.util.List;

public class StatisticController {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    private StatisticDAOService statisticDAOService;
    private XLSFileParserImpl parser;
    private Stage stage;

    private ObservableList<Statistic> observableList = FXCollections.observableArrayList();

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<Statistic> statisticTable;

    @FXML
    private TableColumn<Statistic, String> nameColumn;

    @FXML
    private TableColumn<Statistic, Long> sendCountColumn;

    @FXML
    private TableColumn<Statistic, Long> failedCountColumn;

    @FXML
    private TableColumn<Statistic, String> createdColumn;

    @FXML
    private TableColumn<Statistic, String> modifiedColumn;


    public StatisticController() {
        statisticDAOService = new StatisticDAOService();
        parser = new XLSFileParserImpl();
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/StatisticWindow.fxml"));
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

    public void display() {

    }

    public void readFromFile() {
        List<List<Cell>> rows = parser.readFile(stage);
        for(List<Cell> cells: rows) {
            setColumnValues(cells);
        }
    }

    public void cancel() {
        closeCurrentWindow();
    }

    private void setColumnValues(List<Cell> cells) {
        Statistic statistic = new Statistic();
        observableList.add(statistic);
        displayRecords();
    }

    private void displayRecords() {
        statisticTable.setItems(observableList);
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

