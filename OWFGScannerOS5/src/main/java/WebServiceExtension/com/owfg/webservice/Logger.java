package com.owfg.webservice;

import net.rim.device.api.system.EventLogger;

/**
 * a simple wrapper class which writes strings to the Blackberry built in logger
 * @author Warren Voelkl
 */
public class Logger {
	private static void logEvent(String msg, int level) {
		EventLogger.logEvent(Client.GUID, msg.getBytes(), level);
	}

	public static void logDebugEvent(String msg) {
		logEvent(msg, EventLogger.DEBUG_INFO);
	}

	public static void logInformationEvent(String msg) {
		logEvent(msg, EventLogger.INFORMATION);
	}

	public static void logWarningEvent(String msg) {
		logEvent(msg, EventLogger.WARNING);
	}

	public static void logErrorEvent(String msg) {
		logEvent(msg, EventLogger.ERROR);
	}

	public static void logSevereErrorEvent(String msg) {
		logEvent(msg, EventLogger.SEVERE_ERROR);
	}

	public static void logAlwaysEvent(String msg) {
		logEvent(msg, EventLogger.ALWAYS_LOG);
	}
}
