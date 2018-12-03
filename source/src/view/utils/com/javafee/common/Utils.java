package com.javafee.common;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

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

	public static File displayJFileChooserAndGetFile(String directory) {
		String dir = directory != null ? directory : FileSystemView.getFileSystemView().getHomeDirectory().toString();
		JFileChooser jfc = new JFileChooser(dir);
		jfc.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(Constans.APPLICATION_TEMPLATE_EXTENSION) || f.isDirectory();
			}

			public String getDescription() {
				return Constans.APPLICATION_TEMPLATE_EXTENSION_DESCRIPTION;
			}
		});

		File result = null;

		int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			result = jfc.getSelectedFile();

			Path path = Paths.get(result.getParent());

			if (path.toString().toLowerCase().endsWith(Constans.APPLICATION_TEMPLATE_DIRECTORY_NAME.toLowerCase())) {
				if (result.getAbsolutePath().toString().toLowerCase()
						.endsWith(Constans.APPLICATION_TEMPLATE_EXTENSION.toLowerCase()))
					result = new File(dir, result.getName());
				else
					result = new File(dir, result.getName() + Constans.APPLICATION_TEMPLATE_EXTENSION);
			} else {
				try {
					path = Paths.get(
							result.getParentFile() + File.separator + Constans.APPLICATION_TEMPLATE_DIRECTORY_NAME);
					if (!Files.exists(path)) {
						Files.createDirectories(path);
						if (result.getAbsolutePath().toString().toLowerCase()
								.endsWith(Constans.APPLICATION_TEMPLATE_EXTENSION.toLowerCase()))
							result = new File(path.toString(), result.getName());
						else
							result = new File(path.toString(),
									result.getName() + Constans.APPLICATION_TEMPLATE_EXTENSION);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}
