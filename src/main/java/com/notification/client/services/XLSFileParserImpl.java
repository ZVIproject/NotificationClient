package com.notification.client.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.notification.client.controllers.alerts.IncorrectDataAlert;
import com.notification.client.controllers.alerts.NotEnoughColumnsAlert;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.notification.client.interfaces.LoggerService;
import com.notification.client.interfaces.XLSFileParser;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class XLSFileParserImpl implements XLSFileParser {
	
	private FileChooser fileChooser = new FileChooser();
	
	private LoggerService loggerService;
	
	
	public XLSFileParserImpl() {
		loggerService = new LoggerServiceImpl();
	}

	/**
	 * @see XLSFileParser#readFile(Stage, List<String>)
	 */
	@Override
	public List<List<Cell>> readFile(Stage stage, List<String> columns) {
		File file = openFile(stage);
		
		if(file == null || file.isDirectory()) {
			return null;
		}

		if(file.getName().endsWith(".xlsx")) {
			try {
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				Map<String, Integer> indexes = getIndexes(workbook, columns);

				if (indexes == null) {
					NotEnoughColumnsAlert alert = new NotEnoughColumnsAlert();
					alert.showDialog();
					return new ArrayList<>();
				}

				return getContentOfFile(workbook, new ArrayList<>(indexes.values()));
				
			} catch (InvalidFormatException | IOException e) {
				loggerService.logError(e, "Exception during .xlsx file reading");
				throw new RuntimeException(e);
			}
			
		} else if(file.getName().endsWith(".xls")) {
			try {
				InputStream inputStream = new FileInputStream(file);
				HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

				Map<String, Integer> indexes = getIndexes(workbook, columns);

				if (indexes == null) {
					NotEnoughColumnsAlert alert = new NotEnoughColumnsAlert();
					alert.showDialog();
					return new ArrayList<>();
				}

				return getContentOfFile(workbook, new ArrayList<>(indexes.values()));
				
			} catch (IOException e) {
				loggerService.logError(e, "Exception during .xls file reading");
				throw new RuntimeException(e);
			}
		}	
		
		return null;
	}
	
	private File openFile(Stage stage) {
		File file = fileChooser.showOpenDialog(stage);
		return file;
	}
	
	private List<List<Cell>> getContentOfFile(XSSFWorkbook workbook, List<Integer> values) {
		List<List<Cell>> resultList = new ArrayList<>();
        XSSFSheet currentSheet = workbook.getSheetAt(0);

        for (int i = 1; i <= currentSheet.getLastRowNum(); i++) {
			Row row = currentSheet.getRow(i);

			List<Cell> listOfCells = new ArrayList<>();
			for (Integer numb: values) {
				listOfCells.add(row.getCell(numb));
			}
			resultList.add(listOfCells);
		}

		return resultList;
	}
	
	private List<List<Cell>> getContentOfFile(HSSFWorkbook workbook, List<Integer> values) {
		List<List<Cell>> resultList = new ArrayList<>();
        HSSFSheet currentSheet = workbook.getSheetAt(0);

		for (int i = 1; i <= currentSheet.getLastRowNum(); i++) {
			Row row = currentSheet.getRow(i);

			List<Cell> listOfCells = new ArrayList<>();
			for (Integer numb: values) {
				listOfCells.add(row.getCell(numb));
			}
			resultList.add(listOfCells);
		}

		return resultList;
	}

	private Map<String, Integer> getIndexes(XSSFWorkbook book, List<String> columns) {
		XSSFSheet currentSheet = book.getSheetAt(0);
		Row row = currentSheet.getRow(0);

		Map<String, Integer> map = new HashMap<>();

		for (String column: columns) {
			map.put(column, -1);
			for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
				if (row.getCell(columnNumber).getStringCellValue().equals(column)) {
					map.put(column, columnNumber);
				}
			}
		}

		try {
			for (String key: columns) {
				if (map.get(key) == -1) {
					return null;
				}
			}
			return map;

		} catch(Exception e) {
			return null;
		}
	}

	private Map<String, Integer> getIndexes(HSSFWorkbook book, List<String> columns) {
		HSSFSheet currentSheet = book.getSheetAt(0);
		Row row = currentSheet.getRow(0);

		Map<String, Integer> map = new HashMap<>();

		for (String column: columns) {
			map.put(column, -1);
			for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
				if (row.getCell(columnNumber).getStringCellValue().equals(column)) {
					map.put(column, columnNumber);
				}
			}
		}

		try {
			for (String key: columns) {
				if (map.get(key) == -1) {
					return null;
				}
			}
			return map;

		} catch(Exception e) {
			return null;
		}
	}
	
}
