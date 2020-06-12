package com.javafee.elibrary.core.common;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.oxbow.swingbits.util.Strings;

import com.itextpdf.text.BaseColor;
import com.javafee.elibrary.core.startform.LogInEvent;

public class Utils {
	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static Color getApplicationColor() {
		return Constants.APPLICATION_DEFAULT_COLOR;
	}

	public static Font getApplicationFont() {
		return Constants.APPLICATION_DEFAULT_FONT;
	}

	public static Color getApplicationUserDefinedColor() {
		String color = LogInEvent.getUserData() != null && LogInEvent.getUserData().getSystemProperties() != null ? LogInEvent.getUserData().getSystemProperties().getColor() : null;
		Color userDefinedColor;
		if (Strings.isEmpty(color))
			userDefinedColor = getApplicationColor();
		else {
			String[] rgb = color.split(",");
			userDefinedColor = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
		}
		return userDefinedColor;
	}

	public static BaseColor getApplicationUserDefinedColorAsItextpdfBaseColor() {
		return new BaseColor(getApplicationUserDefinedColor().getRGB());
	}

	public static Font getApplicationUserDefinedFont() {
		String fontName = LogInEvent.getUserData() != null && LogInEvent.getUserData().getSystemProperties() != null ? LogInEvent.getUserData().getSystemProperties().getFontName() : null;
		Integer fontSize = LogInEvent.getUserData() != null && LogInEvent.getUserData().getSystemProperties() != null ? LogInEvent.getUserData().getSystemProperties().getFontSize() : null;
		Font userDefinedFont;
		if (Strings.isEmpty(fontName) && fontSize == null)
			userDefinedFont = getApplicationFont();
		else
			userDefinedFont = new Font(fontName, Font.PLAIN, fontSize);
		return userDefinedFont;
	}

	public static void displayOptionPane(String message, String title, int messageType) {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage("<html>" + message + "</html>");
		optionPane.setMessageType(messageType);
		JDialog dialog = optionPane.createDialog(null, title);
		dialog.setVisible(true);
	}

	public static int displayConfirmDialog(String message, String title) {
		Object[] options = {SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.yes"),
				SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.no")};
		return JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	public static File displayOpenSimpleDialogAndGetImageFile(String directory) {
		String dir = directory != null ? directory : FileSystemView.getFileSystemView().getHomeDirectory().toString();
		JFileChooser jfc = new JFileChooser(dir);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(
				"Image files", ImageIO.getReaderFileSuffixes()));

		File result = null;

		int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			result = jfc.getSelectedFile();
		}

		return result;
	}

	public static File displaySaveDialogAndGetTemplateFile(String directory) {
		String dir = directory != null ? directory : FileSystemView.getFileSystemView().getHomeDirectory().toString();
		JFileChooser jfc = new JFileChooser(dir);
		jfc.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(Constants.APPLICATION_TEMPLATE_EXTENSION) || f.isDirectory();
			}

			public String getDescription() {
				return Constants.APPLICATION_TEMPLATE_EXTENSION_DESCRIPTION;
			}
		});

		File result = null;

		int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			result = jfc.getSelectedFile();

			Path path = Paths.get(result.getParent());

			if (path.toString().toLowerCase().endsWith(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME).getValue().toLowerCase())) {
				if (result.getAbsolutePath().toLowerCase()
						.endsWith(Constants.APPLICATION_TEMPLATE_EXTENSION.toLowerCase()))
					result = new File(path.toString(), result.getName());
				else
					result = new File(path.toString(), result.getName() + Constants.APPLICATION_TEMPLATE_EXTENSION);
			} else {
				try {
					path = Paths.get(
							result.getParentFile() + File.separator + SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME).getValue());
					if (!Files.exists(path)) {
						Files.createDirectories(path);
						if (result.getAbsolutePath().toLowerCase()
								.endsWith(Constants.APPLICATION_TEMPLATE_EXTENSION.toLowerCase()))
							result = new File(path.toString(), result.getName());
						else
							result = new File(path.toString(),
									result.getName() + Constants.APPLICATION_TEMPLATE_EXTENSION);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public static File displayOpenDialogAndGetTemplateFile(String directory) {
		String dir = directory != null ? directory : FileSystemView.getFileSystemView().getHomeDirectory().toString();
		JFileChooser jfc = new JFileChooser(dir);
		jfc.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(Constants.APPLICATION_TEMPLATE_EXTENSION) || f.isDirectory();
			}

			public String getDescription() {
				return Constants.APPLICATION_TEMPLATE_EXTENSION_DESCRIPTION;
			}
		});

		File result = null;

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			result = jfc.getSelectedFile();

			Path path = Paths.get(result.getParent());

			if (path.toString().toLowerCase().endsWith(SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME).getValue().toLowerCase())) {
				if (result.getAbsolutePath().toLowerCase()
						.endsWith(Constants.APPLICATION_TEMPLATE_EXTENSION.toLowerCase()))
					result = new File(path.toString(), result.getName());
				else
					result = new File(path.toString(), result.getName() + Constants.APPLICATION_TEMPLATE_EXTENSION);
			} else {
				try {
					path = Paths.get(
							result.getParentFile() + File.separator + SystemProperties.getInstance().getSystemParameters().get(Constants.APPLICATION_TEMPLATE_DIRECTORY_NAME).getValue());
					if (!Files.exists(path)) {
						Files.createDirectories(path);
						if (result.getAbsolutePath().toLowerCase()
								.endsWith(Constants.APPLICATION_TEMPLATE_EXTENSION.toLowerCase()))
							result = new File(path.toString(), result.getName());
						else
							result = new File(path.toString(),
									result.getName() + Constants.APPLICATION_TEMPLATE_EXTENSION);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public static File displayOpenSimpleDialogAndGetFile(String directory, String extension) {
		String dir = directory != null ? directory : FileSystemView.getFileSystemView().getHomeDirectory().toString();
		JFileChooser jfc = new JFileChooser(dir);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(
				"", ImageIO.getReaderFileSuffixes()));
		File result = null;
		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			result = jfc.getSelectedFile();

			Path path = Paths.get(result.getPath());
			if (!path.toString().toLowerCase().endsWith(extension.toLowerCase()))
				result = new File(path.toString() + extension);
		}
		return result;
	}
}
