package com.javafee.elibrary.core.unicomponent.jtable.actiontable;

import java.awt.*;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ActionJTableBtnEditor extends AbstractCellEditor implements TableCellEditor {
	private final ActionJTableBtnPanel panel = new ActionJTableBtnPanel();
	private final JTable table;
	private Object object;

	public ActionJTableBtnEditor(JTable table) {
		super();
		this.table = table;
	}

	@Override
	public Component getTableCellEditorComponent(
			JTable table, Object value, boolean isSelected, int row, int column) {
		panel.setBackground(table.getSelectionBackground());
		panel.updateButtons(value);
		object = value;
		return panel;
	}

	@Override
	public Object getCellEditorValue() {
		return object;
	}
}
