package com.javafee.exception;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class LogGuiException {
	private static final Logger logger = Logger.getLogger(LogGuiException.class.getName());

	public static void logError(String title, Object message, Throwable t) {
		LogGuiException.createErrorJOptionPane(title, message);
		logger.error(message, t);
	}
	
	public static void logError(String title, Object message) {
		LogGuiException.createErrorJOptionPane(title, message);
		logger.error(message);
	}

	public static void logWarning(String title, Object message) {
		LogGuiException.createWarningJOptionPane(title, message);
		logger.warn(message);
	}

	private static void createErrorJOptionPane(String title, Object message) {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage("<html>" + message + "</html>");
		optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, title);
		dialog.setVisible(true);
	}

	private static void createWarningJOptionPane(String title, Object message) {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage("<html>" + message + "</html>");
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, title);
		dialog.setVisible(true);
	}
}
