package com.javafee.common;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Utils {
	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static Color getApplicationColor() {
		return new Color(237, 245, 248);
	}

	public static void displayOptionPane(String message, String title, int messageType) {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage("<html>" + message + "</html>");
		optionPane.setMessageType(messageType);
		JDialog dialog = optionPane.createDialog(null, title);
		dialog.setVisible(true);
	}

	public static int displayConfirmDialog(String message, String title) {
		Object[] options = { SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.yes"),
				SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.no") };
		return JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
}
