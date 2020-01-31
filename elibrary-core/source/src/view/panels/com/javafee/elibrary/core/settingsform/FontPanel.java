package com.javafee.elibrary.core.settingsform;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Constants.Button_Type;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.unicomponent.jbutton.CustomJButtonFactory;

import lombok.Getter;
import say.swing.JFontChooser;

public class FontPanel extends BasePanel {
	@Getter
	private JFontChooser fontChooser;
	@Getter
	private JButton btnAccept;

	public FontPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblChooseAppFont = new CustomJLabel(SystemProperties.getInstance().getResourceBundle().getString("fontPanel.lblChooseAppFont"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblChooseAppFont, gbc_lblNewLabel);

		fontChooser = new JFontChooser();
		GridBagConstraints gbc_fontChooser = new GridBagConstraints();
		gbc_fontChooser.insets = new Insets(0, 0, 5, 0);
		gbc_fontChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_fontChooser.gridx = 0;
		gbc_fontChooser.gridy = 1;
		add(fontChooser, gbc_fontChooser);

		btnAccept = CustomJButtonFactory.createCustomJButton(Button_Type.ACCEPT);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		add(btnAccept, gbc_btnNewButton);
	}
}
