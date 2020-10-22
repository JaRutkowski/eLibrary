package com.javafee.elibrary.core.uniform;

import java.awt.*;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jcheckbox.CustomJCheckBox;
import com.javafee.elibrary.core.unicomponent.jdatechooser.CustomJDateChooser;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jtextfield.CustomJTextField;
import com.toedter.calendar.JDateChooser;

import lombok.Getter;
import javax.swing.JTextField;

public class AdmBlockPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JCheckBox chckbxIsBlocked;
	@Getter
	private DecisionPanel decisionPanel;
	@Getter
	private JDateChooser dateChooserBlockDate;
	@Getter
	private JTextField textFieldBlockReason;

	public AdmBlockPanel() {
		super();
		setBorder(new CustomTitledBorder(UIManager.getBorder("TitledBorder.border"),
				SystemProperties.getInstance().getResourceBundle()
						.getString("admBlockPanel.admBlockPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 161, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		chckbxIsBlocked = new CustomJCheckBox(SystemProperties.getInstance().getResourceBundle()
				.getString("admBlockPanel.chckbxIsBlocked"));
		chckbxIsBlocked.setBackground(Utils.getApplicationUserDefinedColor());
		GridBagConstraints gbc_chckbxIsBlocked = new GridBagConstraints();
		gbc_chckbxIsBlocked.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxIsBlocked.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxIsBlocked.gridx = 0;
		gbc_chckbxIsBlocked.gridy = 0;
		add(chckbxIsBlocked, gbc_chckbxIsBlocked);

		JLabel lblBlockDate = new CustomJLabel(SystemProperties.getInstance().getResourceBundle()
				.getString("admBlockPanel.lblBlockDate"));
		GridBagConstraints gbc_lblBlockDate = new GridBagConstraints();
		gbc_lblBlockDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblBlockDate.gridx = 1;
		gbc_lblBlockDate.gridy = 0;
		add(lblBlockDate, gbc_lblBlockDate);

		dateChooserBlockDate = new CustomJDateChooser();
		GridBagConstraints gbc_dateChooserBlockDate = new GridBagConstraints();
		gbc_dateChooserBlockDate.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooserBlockDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooserBlockDate.gridx = 2;
		gbc_dateChooserBlockDate.gridy = 0;
		add(dateChooserBlockDate, gbc_dateChooserBlockDate);

		JLabel lblBlockReason = new CustomJLabel(SystemProperties.getInstance().getResourceBundle()
				.getString("admBlockPanel.lblBlockReason"));
		GridBagConstraints gbc_lblBlockReason = new GridBagConstraints();
		gbc_lblBlockReason.anchor = GridBagConstraints.WEST;
		gbc_lblBlockReason.insets = new Insets(0, 0, 5, 5);
		gbc_lblBlockReason.gridx = 0;
		gbc_lblBlockReason.gridy = 1;
		add(lblBlockReason, gbc_lblBlockReason);

		textFieldBlockReason = new CustomJTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 4;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 2;
		add(textFieldBlockReason, gbc_textField);
		textFieldBlockReason.setColumns(10);

		decisionPanel = new DecisionPanel();
		GridBagLayout gridBagLayout_1 = (GridBagLayout) decisionPanel.getLayout();
		gridBagLayout_1.columnWeights = new double[]{0.0, 0.0};
		GridBagConstraints gbc_decisionPanel = new GridBagConstraints();
		gbc_decisionPanel.insets = new Insets(0, 0, 5, 0);
		gbc_decisionPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_decisionPanel.gridx = 3;
		gbc_decisionPanel.gridy = 0;
		add(decisionPanel, gbc_decisionPanel);
	}
}
