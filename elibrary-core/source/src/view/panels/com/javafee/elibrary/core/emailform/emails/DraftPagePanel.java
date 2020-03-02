package com.javafee.elibrary.core.emailform.emails;

import java.awt.*;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.model.DraftTableModel;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jcheckbox.CustomJCheckBox;
import com.javafee.elibrary.core.unicomponent.jcombobox.CustomJComboBox;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jtable.CustomJTable;
import com.javafee.elibrary.core.unicomponent.tablefilterheader.CustomTableFilterHeader;
import com.javafee.elibrary.hibernate.dto.common.UserData;

import lombok.Getter;
import net.coderazzi.filters.gui.TableFilterHeader;

public class DraftPagePanel extends BasePanel {

	private static final long serialVersionUID = -4830782403871579783L;

	@Getter
	private DraftNavigationPanel draftNavigationPanel;
	@Getter
	private JTable draftTable;
	@Getter
	private JCheckBox checkShowOnlySystemCorrespondence;
	private JLabel lblRecipient;
	@Getter
	private JComboBox<UserData> comboBoxRecipient;

	public DraftPagePanel() {
		super();
		setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("draftPagePanel.draftPagePanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		checkShowOnlySystemCorrespondence = new CustomJCheckBox(SystemProperties.getInstance().getResourceBundle()
				.getString("outboxMailPanel.chckbxShowOnlySystemCorrespondence"));
		checkShowOnlySystemCorrespondence.setBackground(Utils.getApplicationUserDefinedColor());
		GridBagConstraints gbc_checkShowOnlySystemCorrespondence = new GridBagConstraints();
		gbc_checkShowOnlySystemCorrespondence.anchor = GridBagConstraints.WEST;
		gbc_checkShowOnlySystemCorrespondence.insets = new Insets(0, 0, 5, 5);
		gbc_checkShowOnlySystemCorrespondence.gridx = 0;
		gbc_checkShowOnlySystemCorrespondence.gridy = 0;
		add(checkShowOnlySystemCorrespondence, gbc_checkShowOnlySystemCorrespondence);

		lblRecipient = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("outboxMailPanel.lblRecipient"));
		GridBagConstraints gbc_lblRecipient = new GridBagConstraints();
		gbc_lblRecipient.anchor = GridBagConstraints.EAST;
		gbc_lblRecipient.insets = new Insets(0, 0, 5, 5);
		gbc_lblRecipient.gridx = 1;
		gbc_lblRecipient.gridy = 0;
		add(lblRecipient, gbc_lblRecipient);

		comboBoxRecipient = new CustomJComboBox<UserData>();
		GridBagConstraints gbc_comboBoxRecipient = new GridBagConstraints();
		gbc_comboBoxRecipient.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxRecipient.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxRecipient.gridx = 2;
		gbc_comboBoxRecipient.gridy = 0;
		add(comboBoxRecipient, gbc_comboBoxRecipient);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);

		draftTable = new CustomJTable();
		@SuppressWarnings("unused")
		TableFilterHeader customTableFilterHeader = new CustomTableFilterHeader(draftTable);
		draftTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		draftTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		draftTable.setModel(new DraftTableModel());
		draftTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(draftTable);

		draftNavigationPanel = new DraftNavigationPanel();
		GridBagConstraints gbc_outboxNavigationPanel = new GridBagConstraints();
		gbc_outboxNavigationPanel.anchor = GridBagConstraints.SOUTH;
		gbc_outboxNavigationPanel.gridwidth = 3;
		gbc_outboxNavigationPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_outboxNavigationPanel.gridx = 0;
		gbc_outboxNavigationPanel.gridy = 3;
		add(draftNavigationPanel, gbc_outboxNavigationPanel);
	}
}
