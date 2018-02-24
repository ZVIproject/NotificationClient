package com.notification.client.interfaces;

public interface LoggerService {

	/**
	 * Log of additional info.
	 * @param message - additional info
	 */
	void logInfo(String message);
	
	/**
	 * Log of error.
	 * @param exception - object of Exception type
	 * @param message - description of exception
	 */
	void logError(Exception exception, String message);
}
