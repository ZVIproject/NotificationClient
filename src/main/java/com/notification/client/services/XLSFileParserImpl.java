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
	 * @see XLSFileParser#readFile(Stage)
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
				return getContentOfFile(workbook);
				
			} catch (InvalidFormatException | IOException e) {
				loggerService.logError(e, "Exception during .xls file reading");
				throw new RuntimeException(e);
			}
			
		} else if(file.getName().endsWith(".xls")) {
			try {
				InputStream inputStream = new FileInputStream(file);
				HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
				return getContentOfFile(workbook);
				
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
	
	private List<List<Cell>> getContentOfFile(XSSFWorkbook workbook) {
		List<List<Cell>> resultList = new ArrayList<>();
        XSSFSheet currentSheet = workbook.getSheetAt(0);

        Iterator<Row> rows = currentSheet.rowIterator();

        if (rows.hasNext()) {
            rows.next();
        }
        while(rows.hasNext()) {
            List<Cell> listOfCells = new ArrayList<>();
            Iterator<Cell> cells = rows.next().cellIterator();
            while(cells.hasNext()) {
                listOfCells.add(cells.next());
            }
            resultList.add(listOfCells);
        }

		return resultList;
	}
	
	private List<List<Cell>> getContentOfFile(HSSFWorkbook workbook) {
		List<List<Cell>> resultList = new ArrayList<>();
        HSSFSheet currentSheet = workbook.getSheetAt(0);

        Iterator<Row> rows = currentSheet.rowIterator();

        if (rows.hasNext()) {
            rows.next();
        }
        while (rows.hasNext()) {
            List<Cell> listOfCells = new ArrayList<>();
            Iterator<Cell> cells = rows.next().cellIterator();
            while (cells.hasNext()) {
                listOfCells.add(cells.next());
            }
            resultList.add(listOfCells);
        }

		return resultList;
	}
	
}
