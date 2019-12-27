package com.javafee.tabbedform;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.javafee.common.BaseForm;
import com.javafee.common.SystemProperties;
import com.javafee.startform.RegistrationPanel;
import com.javafee.tabbedform.admdictionaries.AdmDictionaryPanel;
import com.javafee.tabbedform.admworkers.WorkerTablePanel;
import com.javafee.tabbedform.books.BookTablePanel;
import com.javafee.tabbedform.clients.ClientTablePanel;
import com.javafee.tabbedform.library.LibraryTablePanel;
import com.javafee.tabbedform.loanservice.LoanServicePanel;
import com.javafee.unicomponent.jbutton.CustomJButton;
import com.javafee.unicomponent.jcombobox.CustomJComboBox;
import com.javafee.unicomponent.jlabel.CustomJLabel;
import com.javafee.unicomponent.jtabbedpane.CustomJTabbedPane;

import lombok.Getter;

public class TabbedForm extends BaseForm {
	@Getter
	private JTabbedPane tabbedPane;
	@Getter
	private ClientTablePanel panelClient;
	@Getter
	private WorkerTablePanel panelWorker;
	@Getter
	private LibraryTablePanel panelLibrary;
	@Getter
	private BookTablePanel panelBook;
	@Getter
	private AdmDictionaryPanel panelAdmDictionary;
	@Getter
	private LoanServicePanel panelLoanService;

	@Getter
	private JLabel lblLogInInformation;
	private JLabel lblSystemInformation;

	@Getter
	private JButton btnInformation;
	@Getter
	private JButton btnSettings;
	@Getter
	private JButton btnLogOut;

	@Getter
	private JLabel lblTime;
	@Getter
	private JComboBox<String> comboBoxLanguage;
	@Getter
	private JLabel lblInternetConnectivityStatus;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		super.initialize();
		frame.setBounds(100, 100, 626, 100);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{67, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		lblLogInInformation = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.lblLogInInformation"));
		GridBagConstraints gbc_lblLogInInformation = new GridBagConstraints();
		gbc_lblLogInInformation.anchor = GridBagConstraints.WEST;
		gbc_lblLogInInformation.insets = new Insets(5, 5, 5, 5);
		gbc_lblLogInInformation.gridx = 0;
		gbc_lblLogInInformation.gridy = 0;
		frame.getContentPane().add(lblLogInInformation, gbc_lblLogInInformation);

		lblTime = new CustomJLabel("New label");
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.gridwidth = 5;
		gbc_lblTime.insets = new Insets(5, 0, 5, 5);
		gbc_lblTime.gridx = 1;
		gbc_lblTime.gridy = 0;
		frame.getContentPane().add(lblTime, gbc_lblTime);

		btnInformation = new CustomJButton(
				SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.btnInformation"));
		btnInformation.setIcon(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnInformation-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		btnInformation.setEnabled(false);
		GridBagConstraints gbc_btnInformation = new GridBagConstraints();
		gbc_btnInformation.anchor = GridBagConstraints.EAST;
		gbc_btnInformation.insets = new Insets(5, 0, 5, 5);
		gbc_btnInformation.gridx = 9;
		gbc_btnInformation.gridy = 0;
		frame.getContentPane().add(btnInformation, gbc_btnInformation);

		btnSettings = new CustomJButton("");
		btnSettings.setIcon(new ImageIcon(new ImageIcon(TabbedForm.class.getResource("/images/btnSettings-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.anchor = GridBagConstraints.EAST;
		gbc_btnSettings.insets = new Insets(5, 0, 5, 5);
		gbc_btnSettings.gridx = 11;
		gbc_btnSettings.gridy = 0;
		frame.getContentPane().add(btnSettings, gbc_btnSettings);

		btnLogOut = new CustomJButton(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.btnLogOut"));
		btnLogOut.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnLogOut-ico.png"))
				.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnLogOut = new GridBagConstraints();
		gbc_btnLogOut.anchor = GridBagConstraints.EAST;
		gbc_btnLogOut.insets = new Insets(5, 0, 5, 5);
		gbc_btnLogOut.gridx = 12;
		gbc_btnLogOut.gridy = 0;
		frame.getContentPane().add(btnLogOut, gbc_btnLogOut);

		comboBoxLanguage = new CustomJComboBox<>();
		GridBagConstraints gbc_comboBoxLanguage = new GridBagConstraints();
		gbc_comboBoxLanguage.fill = GridBagConstraints.VERTICAL;
		gbc_comboBoxLanguage.anchor = GridBagConstraints.EAST;
		gbc_comboBoxLanguage.insets = new Insets(5, 0, 5, 0);
		gbc_comboBoxLanguage.gridx = 14;
		gbc_comboBoxLanguage.gridy = 0;
		frame.getContentPane().add(comboBoxLanguage, gbc_comboBoxLanguage);

		lblSystemInformation = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.lblSystemInformation"));
		lblSystemInformation.setForeground(SystemColor.textHighlight);
		GridBagConstraints gbc_lblSystemInformation = new GridBagConstraints();
		gbc_lblSystemInformation.insets = new Insets(0, 0, 0, 5);
		gbc_lblSystemInformation.gridwidth = 12;
		gbc_lblSystemInformation.gridx = 0;
		gbc_lblSystemInformation.gridy = 2;
		frame.getContentPane().add(lblSystemInformation, gbc_lblSystemInformation);

		tabbedPane = new CustomJTabbedPane(SwingConstants.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.gridwidth = 15;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);

		lblInternetConnectivityStatus = new CustomJLabel(
				new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/sign-error-ico.png"))
						.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_lblInternetConnectivityStatus = new GridBagConstraints();
		gbc_lblInternetConnectivityStatus.insets = new Insets(0, 0, 2, 0);
		gbc_lblInternetConnectivityStatus.gridx = 14;
		gbc_lblInternetConnectivityStatus.gridy = 2;
		frame.getContentPane().add(lblInternetConnectivityStatus, gbc_lblInternetConnectivityStatus);

		panelClient = new ClientTablePanel();
		panelLibrary = new LibraryTablePanel();
		panelBook = new BookTablePanel();
		panelLoanService = new LoanServicePanel();
		panelAdmDictionary = new AdmDictionaryPanel();
		panelWorker = new WorkerTablePanel();

		frame.pack();
	}

	public void pack() {
		frame.pack();
	}
}
