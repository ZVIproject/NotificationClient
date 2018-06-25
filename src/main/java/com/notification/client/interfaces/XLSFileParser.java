package com.notification.client.interfaces;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

import javafx.stage.Stage;

public interface XLSFileParser {

	/**
	 * Reads info from the chosen .xls (or .xlsx) file.
	 *
	 * @param stage
	 * 			- the window that called this method
	 * @param columns
	 * 			- array of strings which contains a names of columns
	 */
	List<List<Cell>> readFile(Stage stage, List<String> columns);
}
