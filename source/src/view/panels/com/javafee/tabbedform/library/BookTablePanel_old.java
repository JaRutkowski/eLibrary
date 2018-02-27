package com.javafee.tabbedform.library;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.javafee.common.Utils;
import com.javafee.model.BookTableModel;
import com.javafee.uniform.ReloadPanel;

import net.coderazzi.filters.gui.TableFilterHeader;

public class BookTablePanel_old extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable bookTable;
	private BookFilterPanel bookFilterPanel;
	private ReloadPanel reloadPanel;

	public BookTablePanel_old() {
		setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 477, 407, 0 };
		gridBagLayout.rowHeights = new int[] { 5, 284, 312, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		
		reloadPanel = new ReloadPanel();
		GridBagConstraints gbc_reloadPanel = new GridBagConstraints();
		gbc_reloadPanel.insets = new Insets(0, 0, 5, 5);
		gbc_reloadPanel.fill = GridBagConstraints.BOTH;
		gbc_reloadPanel.gridx = 0;
		gbc_reloadPanel.gridy = 0;
		add(reloadPanel, gbc_reloadPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		bookTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader = new TableFilterHeader(bookTable);
		bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		bookTable.setModel(new BookTableModel());
		bookTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(bookTable);
		
		bookFilterPanel = new BookFilterPanel();
		GridBagConstraints gbc_bookFilterPanel = new GridBagConstraints();
		gbc_bookFilterPanel.gridheight = 2;
		gbc_bookFilterPanel.anchor = GridBagConstraints.NORTH;
		gbc_bookFilterPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_bookFilterPanel.gridx = 1;
		gbc_bookFilterPanel.gridy = 1;
		add(bookFilterPanel, gbc_bookFilterPanel);
	}
	
	public JTable getBookTable() {
		return bookTable;
	}

	public void setBookTable(JTable bookTable) {
		this.bookTable = bookTable;
	}
	
	public ReloadPanel getReloadPanel() {
		return reloadPanel;
	}
	
	public BookFilterPanel getBookFilterPanel() {
		return bookFilterPanel;
	}
}
