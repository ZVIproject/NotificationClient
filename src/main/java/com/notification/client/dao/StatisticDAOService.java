package com.notification.client.dao;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.notification.client.interfaces.ILoggerService;
import com.notification.client.services.LoggerService;

public class StatisticDAOService {

	private static final String URL_CONNECTION = "";
	private static final String GET_REQUEST_METHOD = "GET";
	
	private ILoggerService loggerService;
	
	
	public StatisticDAOService() {
		loggerService = new LoggerService();
	}
	
	public void getStatistic() {
		URL url = getUrl(URL_CONNECTION);
		
		HttpURLConnection connection = getConnection(url);
		
		try {
			connection.setRequestMethod(GET_REQUEST_METHOD);
		} catch (ProtocolException e) {
			loggerService.logError(e, "Exception during request method setting");
			throw new RuntimeException(e);
		}
		
		try {
			int responseCode = connection.getResponseCode();
		} catch (IOException e) {
			loggerService.logError(e, "Exception during response receiving");
			throw new RuntimeException(e);
		}
	}
	
	private URL getUrl(String connectionString) {		
		try {
			 URL url = new URL(connectionString);
			 return url;
			 
		} catch (MalformedURLException e) {
			loggerService.logError(e, "Exception during connection to the server");
			throw new RuntimeException();
		}
	}
	
	private HttpURLConnection getConnection(URL url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			return connection;
			
		} catch (IOException e) {
			loggerService.logError(e, "Exception during connection establishing");
			throw new RuntimeException(e);
		}
	}
}
