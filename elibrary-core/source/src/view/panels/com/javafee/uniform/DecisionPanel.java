package com.javafee.uniform;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.javafee.common.Constants.Button_Type;
import com.javafee.common.Utils;
import com.javafee.unicomponent.jbutton.CustomJButtonFactory;

import lombok.Getter;

public class DecisionPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JButton btnAccept;

	public DecisionPanel() {
		setBackground(Utils.getApplicationUserDefineColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		btnAccept = CustomJButtonFactory.createAcceptJButton(Button_Type.ACCEPT);
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.gridwidth = 2;
		gbc_btnAccept.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccept.gridx = 0;
		gbc_btnAccept.gridy = 0;
		add(btnAccept, gbc_btnAccept);
	}
}
