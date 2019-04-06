package com.javafee.uniform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.startform.RegistrationPanel;

import lombok.Getter;

public class MessageAndAlertPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnContact;

	public MessageAndAlertPanel() {
		setBackground(Utils.getApplicationColor());
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				SystemProperties.getInstance().getResourceBundle()
						.getString("messageAndAlertPanel.messageAndAlertPanelBorderTitle"),
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnContact = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("clientTablePanel.btnContact"));
		btnContact
				.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnContact-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		add(btnContact, gbc_btnNewButton);
	}
}
