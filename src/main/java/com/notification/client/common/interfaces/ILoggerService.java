package com.notification.client.common.interfaces;

public interface ILoggerService {

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
