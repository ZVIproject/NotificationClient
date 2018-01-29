package com.notification.client.common.interfaces;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

import javafx.stage.Stage;

public interface IXLSFileParser {

	/**
	 * Reads info from the chosen .xls (or .xlsx) file.
	 * @param stage - the window that called this method
	 */
	List<List<Cell>> readFile(Stage stage);
}
