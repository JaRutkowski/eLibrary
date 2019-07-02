package com.javafee.tabbedform.books.frames;

import com.javafee.common.Utils;
import com.javafee.model.AuthorTableModel;
import com.javafee.model.CategoryTableModel;
import com.javafee.model.PublishingHouseTableModel;
import com.javafee.startform.RegistrationPanel;
import com.javafee.uniform.CockpitConfirmationPanel;
import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BookAddModFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	@Getter
	private BookDataPanel bookDataPanel;
	@Getter
	private CockpitConfirmationPanel cockpitConfirmationPanel;

	@Getter
	private JTable authorTable;
	@Getter
	private JTable categoryTable;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_categoryTable;
	private JScrollPane scrollPane_publishingHouse;
	@Getter
	private JTable publishingHouseTable;
	@Getter
	private JButton btnRefreshTables;

	public BookAddModFrame() {
		setBackground(Utils.getApplicationColor());
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(BookAddModFrame.class.getResource("/images/splashScreen.jpg")));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 667, 561);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{29, 197, 0};
		gbl_contentPane.rowHeights = new int[]{0, 119, 140, 119, 22, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		bookDataPanel = new BookDataPanel();
		GridBagConstraints gbc_bookDataPanel = new GridBagConstraints();
		gbc_bookDataPanel.gridheight = 4;
		gbc_bookDataPanel.insets = new Insets(0, 0, 5, 5);
		gbc_bookDataPanel.fill = GridBagConstraints.BOTH;
		gbc_bookDataPanel.gridx = 0;
		gbc_bookDataPanel.gridy = 0;
		contentPane.add(bookDataPanel, gbc_bookDataPanel);

		btnRefreshTables = new JButton();
		btnRefreshTables
				.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnReload-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnRefreshTables = new GridBagConstraints();
		gbc_btnRefreshTables.anchor = GridBagConstraints.EAST;
		gbc_btnRefreshTables.insets = new Insets(0, 0, 5, 0);
		gbc_btnRefreshTables.gridx = 1;
		gbc_btnRefreshTables.gridy = 0;
		contentPane.add(btnRefreshTables, gbc_btnRefreshTables);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);

		authorTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tableFilterHeader = new TableFilterHeader(authorTable);
		authorTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		authorTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		authorTable.setModel(new AuthorTableModel());
		authorTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(authorTable);

		scrollPane_categoryTable = new JScrollPane();
		GridBagConstraints gbc_scrollPane_categoryTable = new GridBagConstraints();
		gbc_scrollPane_categoryTable.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_categoryTable.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_categoryTable.gridx = 1;
		gbc_scrollPane_categoryTable.gridy = 2;
		contentPane.add(scrollPane_categoryTable, gbc_scrollPane_categoryTable);

		categoryTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tableCategoryFilterHeader = new TableFilterHeader(categoryTable);
		categoryTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		categoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		categoryTable.setModel(new CategoryTableModel());
		categoryTable.setAutoCreateRowSorter(true);
		scrollPane_categoryTable.setViewportView(categoryTable);

		scrollPane_publishingHouse = new JScrollPane();
		GridBagConstraints gbc_scrollPane_publishingHouse = new GridBagConstraints();
		gbc_scrollPane_publishingHouse.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_publishingHouse.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_publishingHouse.gridx = 1;
		gbc_scrollPane_publishingHouse.gridy = 3;
		contentPane.add(scrollPane_publishingHouse, gbc_scrollPane_publishingHouse);

		publishingHouseTable = new JTable();
		@SuppressWarnings("unused")
		TableFilterHeader tablePublishingHouseFilterHeader = new TableFilterHeader(publishingHouseTable);
		publishingHouseTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		publishingHouseTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		publishingHouseTable.setModel(new PublishingHouseTableModel());
		publishingHouseTable.setAutoCreateRowSorter(true);
		scrollPane_publishingHouse.setViewportView(publishingHouseTable);

		cockpitConfirmationPanel = new CockpitConfirmationPanel();
		GridBagConstraints gbc_cockpitConfirmationPanel = new GridBagConstraints();
		gbc_cockpitConfirmationPanel.gridwidth = 2;
		gbc_cockpitConfirmationPanel.fill = GridBagConstraints.BOTH;
		gbc_cockpitConfirmationPanel.gridx = 0;
		gbc_cockpitConfirmationPanel.gridy = 4;
		contentPane.add(cockpitConfirmationPanel, gbc_cockpitConfirmationPanel);
	}
}
