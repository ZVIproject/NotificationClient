package com.notification.client.controllers;

import com.notification.client.components.entities.Message;
import com.notification.client.rest.MessageDAOService;
import com.notification.client.services.LoggerServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DateFilterController {

    private LoggerServiceImpl logger;
    private MessageDAOService messageDAOService;

    @FXML private TextField from;
    @FXML private TextField to;
    @FXML private Slider toSlider;
    @FXML private Slider fromSlider;
    @FXML private Button confirmButton;
    @FXML private Button clearButton;

    @FXML public void initialize() {
        init();
    }

    public DateFilterController() {
        logger = new LoggerServiceImpl();
        messageDAOService = new MessageDAOService();
    }

    public void showDialog() {
        Stage stage = new Stage();
        BorderPane pane;
        try {
            pane = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("fxmls/DateFilterForm.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Головне вікно");
            stage.setResizable(false);
            stage.show();

        } catch(IOException | NullPointerException e) {
            logger.logError(e, "Exception during form loading");
            throw new RuntimeException(e);
        }
    }

    public void confirm() {
        String fromValue = from.getText();
        String toValue = to.getText();


        SimpleDateFormat formatter = new SimpleDateFormat("dd.MMM.yyyy");

        Date newToValue=null;
        Date newFromValue = null;

        try {

            newFromValue = formatter.parse(fromValue);
            newToValue = formatter.parse(toValue);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        if (!fromValue.equals("")) {
//            newFromValue = new Date((long) fromSlider.getValue());
//        }
//        if (!toValue.equals("")) {
//            newToValue = new Date((long) toSlider.getValue());
//        }

        List<Message> messages = messageDAOService.getMessagesFromTo(newFromValue.getTime(), newToValue.getTime());

        EmailStatisticController.emailStatisticController.setList(messages);
    }

    public void clear() {
        init();
    }

    private void init() {
        Date fromValue = EmailStatisticController.from;
        Date toValue = EmailStatisticController.to;

        toSlider.setMin(fromValue.getTime());
        toSlider.setMax(toValue.getTime());

        fromSlider.setMin(fromValue.getTime());
        fromSlider.setMax(toValue.getTime());

        toSlider.setValue(fromValue.getTime());
        toSlider.setValue(toValue.getTime());

        from.setText(getString(fromValue));
        to.setText(getString(toValue));
    }

    public void updateFrom() {
        Date fromDate = new Date((long) fromSlider.getValue());
        from.setText(getString(fromDate));
    }

    public void updateTo() {
        Date toDate = new Date((long) toSlider.getValue());
        to.setText(getString(toDate));
    }

    private String getString(Date value) {
        Format formater = new SimpleDateFormat("dd.MMMM.yyyy, hh:mm:ss");
        return formater.format(value);
    }

    private List<Message> getActiveMessages(Date from, Date to) {
        return messageDAOService.getMessagesFromTo(from.getTime(), to.getTime());
    }

}