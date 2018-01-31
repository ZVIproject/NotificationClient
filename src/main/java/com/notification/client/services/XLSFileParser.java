package com.notification.client.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.notification.client.interfaces.ILoggerService;
import com.notification.client.interfaces.IXLSFileParser;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class XLSFileParser implements IXLSFileParser {
	
	private FileChooser fileChooser = new FileChooser();
	
	private ILoggerService loggerService;
	
	
	public XLSFileParser() {
		loggerService = new LoggerService();
	}

	/**
	 * @see com.notification.client.common.interfaces.IXLSFileParser#readFile(Stage)
	 */
	@Override
	public List<List<Cell>> readFile(Stage stage) {
		File file = openFile(stage);
		
		if(file == null || file.isDirectory()) {
			return null;
		}
		
		if(file.getName().endsWith(".xlsx")) {
			try {
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				return getContentOfXLSFile(workbook);
				
			} catch (InvalidFormatException | IOException e) {
				loggerService.logError(e, "Exception during .xls file reading");
				throw new RuntimeException(e);
			}
			
		} else if(file.getName().endsWith(".xls")) {
			try {
				InputStream inputStream = new FileInputStream(file);
				HSSFWorkbook workbook = new HSSFWorkbook(inputStream);				
				return getContentOfXLSXFile(workbook);
				
			} catch (IOException e) {
				loggerService.logError(e, "Exception during .xlsx file reading");
				throw new RuntimeException(e);
			}
		}	
		
		return null;
	}
	
	private File openFile(Stage stage) {
		File file = fileChooser.showOpenDialog(stage);
		return file;
	}
	
	private List<List<Cell>> getContentOfXLSFile(XSSFWorkbook workbook) {
		List<List<Cell>> resultList = new ArrayList<>();
		
		for(int i=0; i<workbook.getNumberOfSheets(); i++) {
			XSSFSheet currentSheet = workbook.getSheetAt(i);
			
			Iterator<Row> rows = currentSheet.rowIterator();
			List<Cell> listOfCells = new ArrayList<>();
			
			while(rows.hasNext()) {
				Iterator<Cell> cells = rows.next().cellIterator();
				while(cells.hasNext()) {
					listOfCells.add(cells.next());
				}
			}
			
			resultList.add(listOfCells);
		}
		
		return resultList;
	}
	
	private List<List<Cell>> getContentOfXLSXFile(HSSFWorkbook workbook) {
		List<List<Cell>> resultList = new ArrayList<>();
		
		for(int i=0; i<workbook.getNumberOfSheets(); i++) {
			HSSFSheet currentSheet = workbook.getSheetAt(i);
			
			Iterator<Row> rows = currentSheet.rowIterator();
			List<Cell> listOfCells = new ArrayList<>();
			
			while(rows.hasNext()) {
				Iterator<Cell> cells = rows.next().cellIterator();
				while(cells.hasNext()) {
					listOfCells.add(cells.next());
				}
			}
			
			resultList.add(listOfCells);
		}
		
		return resultList;
	}
	
}
