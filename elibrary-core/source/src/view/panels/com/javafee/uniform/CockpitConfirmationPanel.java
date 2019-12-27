package com.javafee.uniform;

import java.awt.*;

import javax.swing.JButton;

import com.javafee.common.BasePanel;
import com.javafee.common.Constants.Button_Type;
import com.javafee.unicomponent.jbutton.CustomJButtonFactory;

import lombok.Getter;

public class CockpitConfirmationPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnAccept;

	public CockpitConfirmationPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnAccept = CustomJButtonFactory.createCustomJButton(Button_Type.ACCEPT);
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.anchor = GridBagConstraints.NORTH;
		gbc_btnAccept.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccept.gridx = 0;
		gbc_btnAccept.gridy = 0;
		add(btnAccept, gbc_btnAccept);
	}
}
