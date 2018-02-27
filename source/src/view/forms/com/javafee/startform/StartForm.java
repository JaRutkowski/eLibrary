package com.javafee.startform;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.javafee.common.Constans;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.uniform.NavigationPanel;
import javax.swing.UIManager;
import javax.swing.JComboBox;

public class StartForm {
	private JFrame frmElibrary;
	private LogInPanel logInPanel;
	private RegistrationPanel registrationPanel;
	private NavigationPanel navigationPanel;

	private JButton btnLogIn;
	private JButton btnRegistrationMode;
	private JLabel lblSystemInformation;

	public StartForm() {
		initialize();
	}

	public void initialize() {
		Utils.setLookAndFeel();
		frmElibrary = new JFrame();
		frmElibrary.getContentPane().setBackground(Utils.getApplicationColor());
		frmElibrary.setTitle(Constans.APPLICATION_NAME);
		frmElibrary.setIconImage(Toolkit.getDefaultToolkit().getImage(StartForm.class.getResource("/images/splashScreen.jpg")));
		frmElibrary.setBounds(100, 100, 361, 543);
		frmElibrary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 41 };
		gridBagLayout.rowHeights = new int[] { 14, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		frmElibrary.getContentPane().setLayout(gridBagLayout);

		JLabel lblHello = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("startForm.lblHello"));
		lblHello.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblHello = new GridBagConstraints();
		gbc_lblHello.anchor = GridBagConstraints.WEST;
		gbc_lblHello.insets = new Insets(5, 5, 5, 0);
		gbc_lblHello.gridx = 0;
		gbc_lblHello.gridy = 0;
		frmElibrary.getContentPane().add(lblHello, gbc_lblHello);

		logInPanel = new LogInPanel();
		logInPanel.setToolTipText("");
		GridBagLayout gridBagLayout_1 = (GridBagLayout) logInPanel.getLayout();
		gridBagLayout_1.columnWidths = new int[] { 74, 124 };
		GridBagConstraints gbc_logInPanel = new GridBagConstraints();
		gbc_logInPanel.insets = new Insets(20, 20, 5, 20);
		gbc_logInPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_logInPanel.gridx = 0;
		gbc_logInPanel.gridy = 1;
		frmElibrary.getContentPane().add(logInPanel, gbc_logInPanel);

		btnLogIn = new JButton(SystemProperties.getInstance().getResourceBundle().getString("startForm.btnLogIn"));
		btnLogIn.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnLogIn-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		btnLogIn.setBorderPainted(true);
		btnLogIn.setFocusPainted(true);
		btnLogIn.setContentAreaFilled(true);
		GridBagConstraints gbc_btnLogIn = new GridBagConstraints();
		gbc_btnLogIn.anchor = GridBagConstraints.SOUTH;
		gbc_btnLogIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogIn.insets = new Insets(0, 20, 5, 20);
		gbc_btnLogIn.gridx = 0;
		gbc_btnLogIn.gridy = 2;
		frmElibrary.getContentPane().add(btnLogIn, gbc_btnLogIn);

		btnRegistrationMode = new JButton(SystemProperties.getInstance().getResourceBundle().getString("startForm.btnRegistration"));
		btnRegistrationMode.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnRegistrationMode-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnRegistrationMode = new GridBagConstraints();
		gbc_btnRegistrationMode.anchor = GridBagConstraints.NORTH;
		gbc_btnRegistrationMode.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRegistrationMode.insets = new Insets(0, 20, 20, 20);
		gbc_btnRegistrationMode.gridx = 0;
		gbc_btnRegistrationMode.gridy = 3;
		frmElibrary.getContentPane().add(btnRegistrationMode, gbc_btnRegistrationMode);

		registrationPanel = new RegistrationPanel();
		GridBagLayout gridBagLayout_2 = (GridBagLayout) registrationPanel.getLayout();
		gridBagLayout_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		registrationPanel.setVisible(false);

		navigationPanel = new NavigationPanel();
		GridBagConstraints gbc_navigationPanel = new GridBagConstraints();
		gbc_navigationPanel.insets = new Insets(0, 5, 5, 0);
		gbc_navigationPanel.fill = GridBagConstraints.BOTH;
		gbc_navigationPanel.gridx = 0;
		gbc_navigationPanel.gridy = 4;
		navigationPanel.setVisible(false);
		frmElibrary.getContentPane().add(navigationPanel, gbc_navigationPanel);

		GridBagConstraints gbc_registrationPanel = new GridBagConstraints();
		gbc_registrationPanel.insets = new Insets(0, 5, 5, 0);
		gbc_registrationPanel.fill = GridBagConstraints.BOTH;
		gbc_registrationPanel.gridx = 0;
		gbc_registrationPanel.gridy = 5;
		frmElibrary.getContentPane().add(registrationPanel, gbc_registrationPanel);

		lblSystemInformation = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("startForm.lblSystemInformation"));
		lblSystemInformation.setForeground(SystemColor.textHighlight);
		GridBagConstraints gbc_lblSystemInformation = new GridBagConstraints();
		gbc_lblSystemInformation.anchor = GridBagConstraints.SOUTH;
		gbc_lblSystemInformation.gridx = 0;
		gbc_lblSystemInformation.gridy = 6;
		frmElibrary.getContentPane().add(lblSystemInformation, gbc_lblSystemInformation);

		frmElibrary.pack();
	}

	public JFrame getFrame() {
		return frmElibrary;
	}

	public void setFrame(JFrame frame) {
		this.frmElibrary = frame;
	}

	public LogInPanel getLogInPanel() {
		return logInPanel;
	}

	public void setLogInPanel(LogInPanel logInPanel) {
		this.logInPanel = logInPanel;
	}

	public JButton getBtnLogIn() {
		return btnLogIn;
	}

	public void setBtnLogIn(JButton btnLogIn) {
		this.btnLogIn = btnLogIn;
	}

	public JButton getBtnRegistrationMode() {
		return btnRegistrationMode;
	}

	public void setBtnRegistrationMode(JButton btnRegistrationMode) {
		this.btnRegistrationMode = btnRegistrationMode;
	}

	public NavigationPanel getNavigationPanel() {
		return navigationPanel;
	}
	
	public RegistrationPanel getRegistrationPanel() {
		return registrationPanel;
	}
}
