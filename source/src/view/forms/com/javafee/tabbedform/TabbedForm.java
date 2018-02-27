package com.javafee.tabbedform;

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
import javax.swing.JTabbedPane;

import com.javafee.common.Constans;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.startform.RegistrationPanel;
import com.javafee.startform.StartForm;
import com.javafee.tabbedform.admdictionaries.AdmDictionaryPanel;
import com.javafee.tabbedform.admworkers.WorkerTablePanel;
import com.javafee.tabbedform.books.BookTablePanel;
import com.javafee.tabbedform.clients.ClientTablePanel;
import com.javafee.tabbedform.library.BookTablePanel_old;
import com.javafee.tabbedform.library.LibraryTablePanel;
import com.javafee.tabbedform.loanservice.LoanServicePanel;
import com.javafee.tabbedform.loanservice.LoanServicePanel_new;
import java.awt.Font;
import javax.swing.JComboBox;

public class TabbedForm {

	private JFrame frmElibrary;
	
	private JTabbedPane tabbedPane;
	private ClientTablePanel panelClient;
	private WorkerTablePanel panelWorker;
	private LibraryTablePanel panelLibrary;
	private BookTablePanel panelBook;
	private AdmDictionaryPanel panelAdmDictionary;
	private LoanServicePanel panelLoanService;
	private LoanServicePanel_new loanServicePanel_new;
	
	private JLabel lblLogInInformation;
	private JLabel lblSystemInformation;
	
	private JButton btnInformation;
	private JButton btnLogOut;
	private JLabel time;
	private JComboBox comboBoxLanguage;

	public TabbedForm() {
		initialize();
	}
	
	private void initialize() {
		Utils.setLookAndFeel();
		frmElibrary = new JFrame();
		frmElibrary.getContentPane().setBackground(Utils.getApplicationColor());
		frmElibrary.setTitle(Constans.APPLICATION_NAME);
		frmElibrary.setIconImage(Toolkit.getDefaultToolkit().getImage(StartForm.class.getResource("/images/splashScreen.jpg")));
		frmElibrary.setBounds(100, 100, 534, 362);
		frmElibrary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{67, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmElibrary.getContentPane().setLayout(gridBagLayout);
		
		lblLogInInformation = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.lblLogInInformation"));
		GridBagConstraints gbc_lblLogInInformation = new GridBagConstraints();
		gbc_lblLogInInformation.anchor = GridBagConstraints.WEST;
		gbc_lblLogInInformation.insets = new Insets(5, 5, 5, 5);
		gbc_lblLogInInformation.gridx = 0;
		gbc_lblLogInInformation.gridy = 0;
		frmElibrary.getContentPane().add(lblLogInInformation, gbc_lblLogInInformation);
		
		time = new JLabel("New label");
		time.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_time = new GridBagConstraints();
		gbc_time.gridwidth = 5;
		gbc_time.insets = new Insets(0, 0, 5, 5);
		gbc_time.gridx = 1;
		gbc_time.gridy = 0;
		frmElibrary.getContentPane().add(time, gbc_time);
		
		btnInformation = new JButton(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.btnInformation"));
		btnInformation.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnInformation-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		btnInformation.setEnabled(false);
		GridBagConstraints gbc_btnInformation = new GridBagConstraints();
		gbc_btnInformation.anchor = GridBagConstraints.EAST;
		gbc_btnInformation.insets = new Insets(5, 0, 5, 5);
		gbc_btnInformation.gridx = 9;
		gbc_btnInformation.gridy = 0;
		frmElibrary.getContentPane().add(btnInformation, gbc_btnInformation);
		
		btnLogOut = new JButton(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.btnLogOut"));
		btnLogOut.setIcon(new ImageIcon(new ImageIcon(RegistrationPanel.class.getResource("/images/btnLogOut-ico.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_btnLogOut = new GridBagConstraints();
		gbc_btnLogOut.anchor = GridBagConstraints.EAST;
		gbc_btnLogOut.insets = new Insets(5, 0, 5, 5);
		gbc_btnLogOut.gridx = 10;
		gbc_btnLogOut.gridy = 0;
		frmElibrary.getContentPane().add(btnLogOut, gbc_btnLogOut);
		
		comboBoxLanguage = new JComboBox();
		GridBagConstraints gbc_comboBoxLanguage = new GridBagConstraints();
		gbc_comboBoxLanguage.fill = GridBagConstraints.VERTICAL;
		gbc_comboBoxLanguage.anchor = GridBagConstraints.EAST;
		gbc_comboBoxLanguage.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxLanguage.gridx = 12;
		gbc_comboBoxLanguage.gridy = 0;
		frmElibrary.getContentPane().add(comboBoxLanguage, gbc_comboBoxLanguage);
		
		lblSystemInformation = new JLabel(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.lblSystemInformation"));
		lblSystemInformation.setForeground(SystemColor.textHighlight);
		GridBagConstraints gbc_lblSystemInformation = new GridBagConstraints();
		gbc_lblSystemInformation.gridwidth = 13;
		gbc_lblSystemInformation.gridx = 0;
		gbc_lblSystemInformation.gridy = 2;
		frmElibrary.getContentPane().add(lblSystemInformation, gbc_lblSystemInformation);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.gridwidth = 13;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		frmElibrary.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		panelClient = new ClientTablePanel();
//		tabbedPane.addTab(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabClientTitle"), null, panelClient, null);
		
		panelLibrary = new LibraryTablePanel();
//		tabbedPane.addTab(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabLibraryTitle"), null, panelLibrary, null);
		
		panelBook = new BookTablePanel();
//		tabbedPane.addTab(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabBookTitle"), null, panelBook, null);
		
		panelLoanService = new LoanServicePanel();
		loanServicePanel_new = new LoanServicePanel_new();
//		tabbedPane.addTab(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabLoanServiceTitle"), null, loanServicePanel_new, null);
		
		panelAdmDictionary = new AdmDictionaryPanel();
//		tabbedPane.addTab(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabAdmDictionaryTitle"), null, panelAdmDictionary, null);
		
		panelWorker = new WorkerTablePanel();
//		tabbedPane.addTab(SystemProperties.getInstance().getResourceBundle().getString("tabbedForm.tabWorkerTitle"), null, panelWorker, null);
		
		frmElibrary.pack();
	}

	public JFrame getFrame() {
		return frmElibrary;
	}

	public void setFrame(JFrame frame) {
		this.frmElibrary = frame;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JLabel getLblLogInInformation() {
		return lblLogInInformation;
	}

	public void setLblLogInInformation(JLabel lblLogInInformation) {
		this.lblLogInInformation = lblLogInInformation;
	}

	public JButton getBtnInformation() {
		return btnInformation;
	}

	public void setBtnInformation(JButton btnInformation) {
		this.btnInformation = btnInformation;
	}

	public JButton getBtnLogOut() {
		return btnLogOut;
	}

	public void setBtnLogOut(JButton btnLogOut) {
		this.btnLogOut = btnLogOut;
	}

	public ClientTablePanel getPanelClient() {
		return panelClient;
	}
	
	public WorkerTablePanel getPanelWorker() {
		return panelWorker;
	}
	
	public LibraryTablePanel getPanelLibrary() {
		return panelLibrary;
	}
	
	public BookTablePanel getPanelBook() {
		return panelBook;
	}
	
	public AdmDictionaryPanel getPanelAdmDictionary() {
		return panelAdmDictionary;
	}
	
	public LoanServicePanel getPanelLoanService() {
		return panelLoanService;
	}

	public LoanServicePanel_new getLoanServicePanel_new() {
		return loanServicePanel_new;
	}

	public void setLoanServicePanel_new(LoanServicePanel_new loanServicePanel_new) {
		this.loanServicePanel_new = loanServicePanel_new;
	}
	
	public JLabel getTime() {
		return time;
	}
	
	public JComboBox getComboBoxLanguage() {
		return comboBoxLanguage;
	}
	
	public void pack() {
		frmElibrary.pack();
	}
}
