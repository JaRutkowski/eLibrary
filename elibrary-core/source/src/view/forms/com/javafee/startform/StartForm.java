package com.javafee.startform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.javafee.common.Constants;
import com.javafee.common.SystemProperties;
import com.javafee.common.BaseForm;
import com.javafee.uniform.NavigationPanel;

import lombok.Getter;

public class StartForm extends BaseForm {
	@Getter
	private LogInPanel logInPanel;
	@Getter
	private RegistrationPanel registrationPanel;
	@Getter
	private NavigationPanel navigationPanel;

	@Getter
	private JButton btnLogIn;
	@Getter
	private JButton btnRegistrationMode;
	private JLabel lblSystemInformation;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		super.initialize();
		frame.setBounds(100, 100, 361, 543);
		frame.setMinimumSize(Constants.START_FORM_MINIMUM_SIZE);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{41};
		gridBagLayout.rowHeights = new int[]{14, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblHello = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("startForm.lblHello"));
		lblHello.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblHello = new GridBagConstraints();
		gbc_lblHello.anchor = GridBagConstraints.WEST;
		gbc_lblHello.insets = new Insets(5, 5, 5, 0);
		gbc_lblHello.gridx = 0;
		gbc_lblHello.gridy = 0;
		frame.getContentPane().add(lblHello, gbc_lblHello);

		logInPanel = new LogInPanel();
		logInPanel.setToolTipText("");
		GridBagLayout gridBagLayout_1 = (GridBagLayout) logInPanel.getLayout();
		gridBagLayout_1.columnWidths = new int[]{74, 124};
		GridBagConstraints gbc_logInPanel = new GridBagConstraints();
		gbc_logInPanel.insets = new Insets(20, 20, 5, 20);
		gbc_logInPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_logInPanel.gridx = 0;
		gbc_logInPanel.gridy = 1;
		frame.getContentPane().add(logInPanel, gbc_logInPanel);

		btnLogIn = new JButton(SystemProperties.getInstance().getResourceBundle().getString("startForm.btnLogIn"));
		btnLogIn.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnLogIn-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		btnLogIn.setBorderPainted(true);
		btnLogIn.setFocusPainted(true);
		btnLogIn.setContentAreaFilled(true);
		GridBagConstraints gbc_btnLogIn = new GridBagConstraints();
		gbc_btnLogIn.anchor = GridBagConstraints.SOUTH;
		gbc_btnLogIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogIn.insets = new Insets(0, 20, 5, 20);
		gbc_btnLogIn.gridx = 0;
		gbc_btnLogIn.gridy = 2;
		frame.getContentPane().add(btnLogIn, gbc_btnLogIn);

		btnRegistrationMode = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("startForm.btnRegistration"));
		btnRegistrationMode.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnRegistrationMode-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnRegistrationMode = new GridBagConstraints();
		gbc_btnRegistrationMode.anchor = GridBagConstraints.NORTH;
		gbc_btnRegistrationMode.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRegistrationMode.insets = new Insets(0, 20, 20, 20);
		gbc_btnRegistrationMode.gridx = 0;
		gbc_btnRegistrationMode.gridy = 3;
		frame.getContentPane().add(btnRegistrationMode, gbc_btnRegistrationMode);

		registrationPanel = new RegistrationPanel();
		GridBagLayout gridBagLayout_2 = (GridBagLayout) registrationPanel.getLayout();
		gridBagLayout_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		registrationPanel.setVisible(false);

		navigationPanel = new NavigationPanel();
		GridBagConstraints gbc_navigationPanel = new GridBagConstraints();
		gbc_navigationPanel.insets = new Insets(0, 5, 5, 0);
		gbc_navigationPanel.fill = GridBagConstraints.BOTH;
		gbc_navigationPanel.gridx = 0;
		gbc_navigationPanel.gridy = 4;
		navigationPanel.setVisible(false);
		frame.getContentPane().add(navigationPanel, gbc_navigationPanel);

		GridBagConstraints gbc_registrationPanel = new GridBagConstraints();
		gbc_registrationPanel.insets = new Insets(0, 5, 5, 0);
		gbc_registrationPanel.fill = GridBagConstraints.BOTH;
		gbc_registrationPanel.gridx = 0;
		gbc_registrationPanel.gridy = 5;
		frame.getContentPane().add(registrationPanel, gbc_registrationPanel);

		lblSystemInformation = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("startForm.lblSystemInformation"));
		lblSystemInformation.setForeground(SystemColor.textHighlight);
		GridBagConstraints gbc_lblSystemInformation = new GridBagConstraints();
		gbc_lblSystemInformation.anchor = GridBagConstraints.SOUTH;
		gbc_lblSystemInformation.gridx = 0;
		gbc_lblSystemInformation.gridy = 6;
		frame.getContentPane().add(lblSystemInformation, gbc_lblSystemInformation);

		frame.pack();
	}
}
