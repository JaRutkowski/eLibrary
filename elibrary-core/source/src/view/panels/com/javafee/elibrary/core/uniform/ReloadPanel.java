package com.javafee.elibrary.core.uniform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.RegistrationPanel;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButton;

import lombok.Getter;

public class ReloadPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnReload;

	public ReloadPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnReload = new CustomJButton(SystemProperties.getInstance().getResourceBundle().getString("reloadPanel.btnReload"));
		btnReload.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnReload-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnReload = new GridBagConstraints();
		gbc_btnReload.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnReload.gridx = 0;
		gbc_btnReload.gridy = 0;
		add(btnReload, gbc_btnReload);
	}
}
