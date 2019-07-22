package com.javafee.uniform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.javafee.common.BasePanel;
import com.javafee.common.SystemProperties;
import com.javafee.startform.RegistrationPanel;
import com.javafee.unicomponent.border.CustomTitledBorder;
import com.javafee.unicomponent.jbutton.CustomJButton;

import lombok.Getter;

public class MessageAndAlertPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnContact;

	public MessageAndAlertPanel() {
		super();
		setBorder(new CustomTitledBorder(UIManager.getBorder("TitledBorder.border"),
				SystemProperties.getInstance().getResourceBundle()
						.getString("messageAndAlertPanel.messageAndAlertPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnContact = new CustomJButton(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnContact-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)),
				SystemProperties.getInstance().getResourceBundle().getString("clientTablePanel.btnContact")
		);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		add(btnContact, gbc_btnNewButton);
	}
}
