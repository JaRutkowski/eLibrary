package com.javafee.elibrary.core.uniform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;
import com.javafee.elibrary.core.unicomponent.jcheckbox.CustomJCheckBox;
import com.javafee.elibrary.core.unicomponent.jdatechooser.CustomJDateChooser;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jtextfield.CustomJTextField;
import com.toedter.calendar.JDateChooser;

import lombok.Getter;

public class AdmBlockPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JCheckBox chckbxIsBlocked;
	@Getter
	private JLabel lblBlockDate;
	@Getter
	private JDateChooser dateChooserBlockDate;
	@Getter
	private JLabel lblBlockReason;
	@Getter
	private JTextField textFieldBlockReason;
	@Getter
	private JButton btnBlock;
	@Getter
	private JButton btnUnblock;

	public AdmBlockPanel() {
		super();
		setBorder(new CustomTitledBorder(UIManager.getBorder("TitledBorder.border"),
				SystemProperties.getInstance().getResourceBundle()
						.getString("admBlockPanel.admBlockPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 156, 71, 38, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
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

		lblBlockDate = new CustomJLabel(SystemProperties.getInstance().getResourceBundle()
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

		lblBlockReason = new CustomJLabel(SystemProperties.getInstance().getResourceBundle()
				.getString("admBlockPanel.lblBlockReason"));
		GridBagConstraints gbc_lblBlockReason = new GridBagConstraints();
		gbc_lblBlockReason.gridwidth = 5;
		gbc_lblBlockReason.anchor = GridBagConstraints.WEST;
		gbc_lblBlockReason.insets = new Insets(0, 0, 5, 5);
		gbc_lblBlockReason.gridx = 0;
		gbc_lblBlockReason.gridy = 1;
		add(lblBlockReason, gbc_lblBlockReason);

		textFieldBlockReason = new CustomJTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridwidth = 5;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 2;
		add(textFieldBlockReason, gbc_textField);
		textFieldBlockReason.setColumns(10);

		btnBlock = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAccept-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("admBlockPanel.btnBlock"));
		GridBagConstraints gbc_btnBlock = new GridBagConstraints();
		gbc_btnBlock.anchor = GridBagConstraints.EAST;
		gbc_btnBlock.insets = new Insets(0, 0, 5, 5);
		gbc_btnBlock.gridx = 3;
		gbc_btnBlock.gridy = 0;
		add(btnBlock, gbc_btnBlock);

		btnUnblock = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnDeny-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("admBlockPanel.btnUnblock"));
		GridBagConstraints gbc_btnUnblock = new GridBagConstraints();
		gbc_btnUnblock.anchor = GridBagConstraints.WEST;
		gbc_btnUnblock.insets = new Insets(0, 0, 5, 0);
		gbc_btnUnblock.gridx = 4;
		gbc_btnUnblock.gridy = 0;
		add(btnUnblock, gbc_btnUnblock);
	}
}
