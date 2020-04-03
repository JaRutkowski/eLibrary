package com.javafee.elibrary.core.unicomponent.jtable.actiontable;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ActionJTableBtnRenderer implements TableCellRenderer {
	private ActionJTableBtnPanel actionJTableBtnPanel = new ActionJTableBtnPanel();

	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		actionJTableBtnPanel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
		actionJTableBtnPanel.updateButtons(value);
		return actionJTableBtnPanel;
	}
}
