package com.javafee.uniform;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.startform.RegistrationPanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class CockpitConfirmationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnAccept;

	public CockpitConfirmationPanel() {
		setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnAccept = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("cockpitConfirmationPanel.btnAccept"));
		btnAccept.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnAccept-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.anchor = GridBagConstraints.NORTH;
		gbc_btnAccept.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccept.gridx = 0;
		gbc_btnAccept.gridy = 0;
		add(btnAccept, gbc_btnAccept);
	}
}
