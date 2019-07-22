package com.javafee.settingsform;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

import com.javafee.common.BasePanel;
import com.javafee.common.Constants.Button_Type;
import com.javafee.common.SystemProperties;
import com.javafee.unicomponent.jbutton.CustomJButtonFactory;
import com.javafee.unicomponent.jlabel.CustomJLabel;

import lombok.Getter;

public class ThemePanel extends BasePanel {
	@Getter
	private JColorChooser colorChooser;
	@Getter
	private JButton btnAccept;

	public ThemePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblChooseAppTheme = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("themePanel.lblChooseAppTheme"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(5, 5, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblChooseAppTheme, gbc_lblNewLabel);

		colorChooser = new JColorChooser();
		GridBagConstraints gbc_colorChooser = new GridBagConstraints();
		gbc_colorChooser.insets = new Insets(0, 5, 5, 0);
		gbc_colorChooser.gridx = 0;
		gbc_colorChooser.gridy = 1;
		add(colorChooser, gbc_colorChooser);

		btnAccept = CustomJButtonFactory.createCustomJButton(Button_Type.ACCEPT);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		add(btnAccept, gbc_btnNewButton);
	}
}
