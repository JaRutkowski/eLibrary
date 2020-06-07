package com.javafee.elibrary.core.unicomponent.action;

import com.javafee.elibrary.core.common.filemapper.FileMapper;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.Action;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.ActionWrapper;
import com.javafee.elibrary.core.unicomponent.jtable.imortexportable.ImportExportableJTable;

public class BtnImportAction extends Action {
	private FileMapper fileMapper;
	private ImportExportableJTable importExportableJTable;

	public BtnImportAction(ImportExportableJTable importExportableJTable) {
		super(null, null);
		setAction(new ActionWrapper(e -> onClickBtnImport()));
		this.importExportableJTable = importExportableJTable;
	}

	//TODO Implement while developing import process.
	private void onClickBtnImport() {
	}
}
