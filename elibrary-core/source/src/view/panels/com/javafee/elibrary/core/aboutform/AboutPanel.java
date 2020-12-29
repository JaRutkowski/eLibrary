package com.javafee.elibrary.core.aboutform;

import java.awt.*;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.Box;
import javax.swing.JLabel;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.Utils;
import com.javafee.elibrary.core.unicomponent.jlabel.CustomJLabel;
import com.javafee.elibrary.core.uniform.ImagePanel;

import lombok.Getter;

public class AboutPanel extends BasePanel {
	@Getter
	private CustomJLabel lblVersion;
	@Getter
	private CustomJLabel lblLicenseInformation;
	@Getter
	private CustomJLabel lblSubscription;

	public AboutPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JLabel lblELibraryTitle = new CustomJLabel(MessageFormat.format(SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.lblELibraryTitle"),
				Constants.APPLICATION_NAME));
		lblELibraryTitle.setFont(Utils.getApplicationUserDefinedFont(Constants.APPLICATION_DEFAULT_FONT_BIG_SIZE_DIFF));
		GridBagConstraints gbc_lblELibraryTitle = new GridBagConstraints();
		gbc_lblELibraryTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblELibraryTitle.gridx = 0;
		gbc_lblELibraryTitle.gridy = 1;
		add(lblELibraryTitle, gbc_lblELibraryTitle);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 2;
		add(verticalStrut, gbc_verticalStrut);

		CustomJLabel lblELibrarySubTitle = new CustomJLabel(MessageFormat.format(SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.lblELibrarySubTitle"),
				SystemProperties.getInstance().getResourceBundle().getString("startForm.lblSystemInformation")));
		GridBagConstraints gbc_lblELibrarySubTitle = new GridBagConstraints();
		gbc_lblELibrarySubTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblELibrarySubTitle.gridx = 0;
		gbc_lblELibrarySubTitle.gridy = 3;
		add(lblELibrarySubTitle, gbc_lblELibrarySubTitle);

		lblVersion = new CustomJLabel(MessageFormat.format(SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.lblVersion"),
				"", ""));
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.insets = new Insets(0, 0, 5, 0);
		gbc_lblVersion.gridx = 0;
		gbc_lblVersion.gridy = 4;
		add(lblVersion, gbc_lblVersion);

		lblLicenseInformation = new CustomJLabel();
		GridBagConstraints gbc_lblLicenseInformation = new GridBagConstraints();
		gbc_lblLicenseInformation.insets = new Insets(0, 0, 5, 0);
		gbc_lblLicenseInformation.gridx = 0;
		gbc_lblLicenseInformation.gridy = 5;
		add(lblLicenseInformation, gbc_lblLicenseInformation);

		lblSubscription = new CustomJLabel();
		GridBagConstraints gbc_lblSubscription = new GridBagConstraints();
		gbc_lblSubscription.gridx = 0;
		gbc_lblSubscription.gridy = 6;
		add(lblSubscription, gbc_lblSubscription);

		ImagePanel imagePanel = new ImagePanel(null);
		try {
			imagePanel.loadImage(AboutPanel.class.getResource("/images/splashScreen.jpg").getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		GridBagConstraints gbc_imagePanel = new GridBagConstraints();
		gbc_imagePanel.insets = new Insets(0, 0, 5, 0);
		gbc_imagePanel.fill = GridBagConstraints.BOTH;
		gbc_imagePanel.gridx = 0;
		gbc_imagePanel.gridy = 0;
		add(imagePanel, gbc_imagePanel);

	}

}
