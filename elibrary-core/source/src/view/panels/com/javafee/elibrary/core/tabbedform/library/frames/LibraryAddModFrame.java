package com.javafee.elibrary.core.tabbedform.library.frames;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.model.BookTableModel;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jtable.CustomJTable;
import com.javafee.elibrary.core.unicomponent.jtextfield.CustomJTextField;
import com.javafee.elibrary.core.unicomponent.tablefilterheader.CustomTableFilterHeader;
import com.javafee.elibrary.core.uniform.CockpitConfirmationPanel;

import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

public class LibraryAddModFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	@Getter
	private JTable bookTable;
	private JScrollPane scrollPane;
	@Getter
	private CockpitConfirmationPanel cockpitConfirmationPanel;
	private JLabel lblInventoryNumber;
	@Getter
	private JTextField textFieldInventoryNumber;

	public LibraryAddModFrame() {
		setBackground(Utils.getApplicationUserDefinedColor());
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(LibraryAddModFrame.class.getResource("/images/splashScreen.jpg")));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 517, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{97, 345, 0};
		gbl_contentPane.rowHeights = new int[]{317, 0, 22, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);

		bookTable = new CustomJTable();
		@SuppressWarnings("unused")
		TableFilterHeader customTableFilterHeader = new CustomTableFilterHeader(bookTable);
		bookTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		bookTable.setModel(new BookTableModel());
		bookTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(bookTable);

		lblInventoryNumber = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("libraryAddModFrame.lblInventoryNumber"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblInventoryNumber, gbc_lblNewLabel);

		textFieldInventoryNumber = new CustomJTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		contentPane.add(textFieldInventoryNumber, gbc_textField);
		textFieldInventoryNumber.setColumns(10);

		cockpitConfirmationPanel = new CockpitConfirmationPanel();
		GridBagConstraints gbc_cockpitConfirmationPanel = new GridBagConstraints();
		gbc_cockpitConfirmationPanel.anchor = GridBagConstraints.SOUTH;
		gbc_cockpitConfirmationPanel.gridwidth = 2;
		gbc_cockpitConfirmationPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_cockpitConfirmationPanel.gridx = 0;
		gbc_cockpitConfirmationPanel.gridy = 2;
		contentPane.add(cockpitConfirmationPanel, gbc_cockpitConfirmationPanel);
	}
}
