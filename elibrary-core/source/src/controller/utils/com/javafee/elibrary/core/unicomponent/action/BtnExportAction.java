package com.javafee.elibrary.core.unicomponent.action;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.common.factory.SingletonFactory;
import com.javafee.elibrary.core.common.filemapper.CSVMapper;
import com.javafee.elibrary.core.common.filemapper.ExcelMapper;
import com.javafee.elibrary.core.common.filemapper.FileMapper;
import com.javafee.elibrary.core.common.filemapper.PDFMapper;
import com.javafee.elibrary.core.exception.LogGuiException;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.Action;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.ActionWrapper;
import com.javafee.elibrary.core.unicomponent.jtable.imortexportable.ImportExportableJTable;

public class BtnExportAction extends Action {
	private FileMapper fileMapper;
	private ImportExportableJTable importExportableJTable;

	public BtnExportAction(ImportExportableJTable importExportableJTable) {
		super(null, null);
		setAction(new ActionWrapper(e -> onClickBtnExport()));
		this.importExportableJTable = importExportableJTable;
	}

	private void onClickBtnExport() {
		if (importExportableJTable.getComboBoxExport().getSelectedItem() != null) {
			if (Constants.APPLICATION_CSV_EXTENSION.equals(((ImageIcon) importExportableJTable.getComboBoxExport().getSelectedItem()).getDescription()))
				handleCSVExport();
			if (Constants.APPLICATION_XLSX_EXTENSION.equals(((ImageIcon) importExportableJTable.getComboBoxExport().getSelectedItem()).getDescription()))
				handleExcelExport();
			if (Constants.APPLICATION_PDF_EXTENSION.equals(((ImageIcon) importExportableJTable.getComboBoxExport().getSelectedItem()).getDescription()))
				handlePDFExport();
		}
	}

	private void handleCSVExport() {
		fileMapper = new FileMapper(SingletonFactory.getInstance(CSVMapper.class));
		handleFileExport(fileMapper, Constants.APPLICATION_CSV_EXTENSION);
	}

	private void handleExcelExport() {
		fileMapper = new FileMapper(SingletonFactory.getInstance(ExcelMapper.class));
		handleFileExport(fileMapper, Constants.APPLICATION_XLS_EXTENSION);
	}

	private void handlePDFExport() {
		fileMapper = new FileMapper(SingletonFactory.getInstance(PDFMapper.class));
		handleFileExport(fileMapper, Constants.APPLICATION_PDF_EXTENSION);
	}

	private void handleFileExport(FileMapper fileMapper, String fileExtension) {
		File file = Utils.displayOpenSimpleDialogAndGetFile(null, fileExtension);
		try {
			if (file != null) {
				file = fileMapper.to(Common.extractDataFromTableModel((AbstractTableModel) importExportableJTable.getTable().getModel(), true),
						Paths.get(file.getPath()));
				Utils.displayOptionPane(
						MessageFormat.format(SystemProperties.getInstance().getResourceBundle()
								.getString("btnExportAction.exportFileSuccess"), file != null ? file.getAbsolutePath() : ""),
						SystemProperties.getInstance().getResourceBundle().getString(
								"btnExportAction.exportFileSuccessTitle"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (IOException e) {
			LogGuiException.logError(
					SystemProperties.getInstance().getResourceBundle()
							.getString("errorDialog.title"),
					MessageFormat.format(SystemProperties.getInstance().getResourceBundle()
							.getString("btnExportAction.exportFileError"), file != null ? file.getAbsolutePath() : ""),
					e);
		}
	}
}
