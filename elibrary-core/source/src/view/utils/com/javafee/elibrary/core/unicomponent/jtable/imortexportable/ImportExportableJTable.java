package com.javafee.elibrary.core.unicomponent.jtable.imortexportable;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.tabbedform.clients.ClientTablePanel;
import com.javafee.elibrary.core.unicomponent.jcombobox.CustomJComboBox;
import com.javafee.elibrary.core.unicomponent.jtable.CustomJTable;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.Action;
import com.javafee.elibrary.core.unicomponent.tablefilterheader.CustomTableFilterHeader;

import lombok.Getter;
import lombok.extern.java.Log;
import net.coderazzi.filters.gui.TableFilterHeader;

@Log
@Getter
public class ImportExportableJTable extends BasePanel {
	private JTable table;
	private JComboBox comboBoxImport;
	private JComboBox comboBoxExport;
	private JPanel panel;

	private Action comboBoxExportItemListener = null;
	private Action comboBoxImportItemListener = null;

	public ImportExportableJTable(List<ImageIcon> comboBoxItems, AbstractTableModel tableModel, boolean defaultTableConfiguration) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.insets = new Insets(5, 0, 5, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		comboBoxImport = new CustomJComboBox<>(comboBoxItems.toArray());
		//TODO Uncomment while implementing import process.
		comboBoxImport.setVisible(false);
		GridBagConstraints gbc_comboBoxImport = new GridBagConstraints();
		gbc_comboBoxImport.anchor = GridBagConstraints.EAST;
		gbc_comboBoxImport.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxImport.gridx = 0;
		gbc_comboBoxImport.gridy = 0;
		panel.add(comboBoxImport, gbc_comboBoxImport);

		comboBoxExport = new CustomJComboBox<>(comboBoxItems.toArray());
		GridBagConstraints gbc_comboBoxExport = new GridBagConstraints();
		gbc_comboBoxExport.anchor = GridBagConstraints.EAST;
		gbc_comboBoxExport.gridx = 1;
		gbc_comboBoxExport.gridy = 0;
		panel.add(comboBoxExport, gbc_comboBoxExport);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);

		table = new CustomJTable();
		if (defaultTableConfiguration) {
			@SuppressWarnings("unused")
			TableFilterHeader customTableFilterHeader = new CustomTableFilterHeader(table);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		table.setModel(tableModel);
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
	}

	public void setActions(Action btnImportAction, Action btnExportAction) {
		try {
			comboBoxExport.addActionListener(btnExportAction.getAction());
			comboBoxExport.setRenderer(new MyComboBoxRenderer(new ImageIcon(new ImageIcon(ImageIO.read(ClientTablePanel.class.getResource("/images/export-ico.png")))
					.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH))));
			comboBoxExport.setSelectedIndex(-1);
			comboBoxImport.addActionListener(btnImportAction.getAction());
			comboBoxImport.setRenderer(new MyComboBoxRenderer(new ImageIcon(new ImageIcon(ImageIO.read(ClientTablePanel.class.getResource("/images/import-ico.png")))
					.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH))));
			comboBoxImport.setSelectedIndex(-1);
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
	}

	public TableModel getModel() {
		return table.getModel();
	}

	public void setModel(TableModel tableModel) {
		table.setModel(tableModel);
	}

	public ListSelectionModel getSelectionModel() {
		return table.getSelectionModel();
	}

	public int getSelectedRow() {
		return table.getSelectedRow();
	}

	public int convertRowIndexToModel(int viewRowIndex) {
		return table.convertRowIndexToModel(viewRowIndex);
	}
}

class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
	private ImageIcon _icon;

	public MyComboBoxRenderer(ImageIcon title) {
		_icon = title;
		setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
	                                              int index, boolean isSelected, boolean hasFocus) {
		UIDefaults defaults = javax.swing.UIManager.getDefaults();
		if (index == -1 && value == null) {
			setIcon(_icon);
			setOpaque(false);
		} else setIcon((Icon) value);
		if ((index != -1 && value != null) && isSelected) {
			setBackground(defaults.getColor("List.selectionBackground"));
			setOpaque(true);
		} else {
			setBackground(defaults.getColor("List.background"));
			setOpaque(false);
		}
		return this;
	}
}