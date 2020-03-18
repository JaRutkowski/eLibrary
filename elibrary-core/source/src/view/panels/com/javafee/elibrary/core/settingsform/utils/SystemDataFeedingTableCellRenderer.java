package com.javafee.elibrary.core.settingsform.utils;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;

public class SystemDataFeedingTableCellRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value != null && value instanceof String
				&& SystemProperties.getInstance().getResourceBundle().getString("systemDataFeedingPanelEvent.validationDataFailed").equals(value)) {
			this.setForeground(Color.RED);
			this.setFont(this.getFont().deriveFont(Font.ITALIC));
		} else {
			this.setForeground(Color.GREEN);
			this.setFont(Constants.APPLICATION_DEFAULT_FONT);
		}
		return this;
	}
}
