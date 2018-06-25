package com.notification.client.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.notification.client.interfaces.LoggerService;

public class LoggerServiceImpl implements LoggerService {

	private static final Logger logger = Logger.getLogger(LoggerServiceImpl.class.getName());

	/**
	 * @see com.notification.client.interfaces.LoggerService#logInfo(String)
	 */
	@Override
	public void logInfo(String message) {
		logger.info(message);
	}
	
	/**
	 * @see com.notification.client.interfaces.LoggerService#logError(Exception, String)
	 */
	@Override
	public void logError(Exception exception, String message) {
		logger.log(Level.SEVERE, message + "\nCause - " + exception.getCause() 
				+ "\nMessage - " + exception.getMessage() 
				+ "\n" + exception.getStackTrace()
			);
	}
}
