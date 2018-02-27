package com.javafee.tabbedform.loanservice;

import javax.swing.JPanel;

import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.tabbedform.library.BookFilterPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;

public class LoanServicePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private LoanDataPanel loanDataPanel;
	private BookFilterPanel bookFilterPanel;
	private VolumeTablePanel volumeTablePanel;
	private LoanTablePanel loanTablePanel;
	private JButton btnLoan;
	private JButton btnReturn;
	private JButton btnProlongation;
	private JButton btnPenalty;
	
	public LoanServicePanel() {
		setBackground(Utils.getApplicationColor());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{43, 156, 200, 85, 400, 344, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{5.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		loanDataPanel = new LoanDataPanel();
		GridBagConstraints gbc_loanDataPanel = new GridBagConstraints();
		gbc_loanDataPanel.gridwidth = 2;
		gbc_loanDataPanel.insets = new Insets(0, 0, 5, 5);
		gbc_loanDataPanel.fill = GridBagConstraints.BOTH;
		gbc_loanDataPanel.gridx = 0;
		gbc_loanDataPanel.gridy = 0;
		add(loanDataPanel, gbc_loanDataPanel);
		
		bookFilterPanel = new BookFilterPanel();
		GridBagConstraints gbc_bookFilterPanel = new GridBagConstraints();
		gbc_bookFilterPanel.gridwidth = 2;
		gbc_bookFilterPanel.insets = new Insets(0, 0, 5, 5);
		gbc_bookFilterPanel.fill = GridBagConstraints.BOTH;
		gbc_bookFilterPanel.gridx = 2;
		gbc_bookFilterPanel.gridy = 0;
		add(bookFilterPanel, gbc_bookFilterPanel);
		
		volumeTablePanel = new VolumeTablePanel();
		GridBagConstraints gbc_volumeTablePanel = new GridBagConstraints();
		gbc_volumeTablePanel.gridwidth = 2;
		gbc_volumeTablePanel.insets = new Insets(0, 0, 5, 0);
		gbc_volumeTablePanel.fill = GridBagConstraints.BOTH;
		gbc_volumeTablePanel.gridx = 4;
		gbc_volumeTablePanel.gridy = 0;
		add(volumeTablePanel, gbc_volumeTablePanel);
		
		btnLoan = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnLoan"));
		GridBagConstraints gbc_btnLoan = new GridBagConstraints();
		gbc_btnLoan.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoan.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoan.gridx = 0;
		gbc_btnLoan.gridy = 1;
		add(btnLoan, gbc_btnLoan);
		
		btnProlongation = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnProlongation"));
		GridBagConstraints gbc_btnProlongation = new GridBagConstraints();
		gbc_btnProlongation.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProlongation.insets = new Insets(0, 0, 5, 5);
		gbc_btnProlongation.gridx = 1;
		gbc_btnProlongation.gridy = 1;
		add(btnProlongation, gbc_btnProlongation);
		
		btnReturn = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnReturn"));
		GridBagConstraints gbc_btnReturn = new GridBagConstraints();
		gbc_btnReturn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReturn.insets = new Insets(0, 0, 5, 5);
		gbc_btnReturn.gridx = 0;
		gbc_btnReturn.gridy = 2;
		add(btnReturn, gbc_btnReturn);
		
		btnPenalty = new JButton(SystemProperties.getInstance().getResourceBundle().getString("loanServicePanel.btnPenalty"));
		GridBagConstraints gbc_btnPenalty = new GridBagConstraints();
		gbc_btnPenalty.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPenalty.insets = new Insets(0, 0, 5, 5);
		gbc_btnPenalty.gridx = 1;
		gbc_btnPenalty.gridy = 2;
		add(btnPenalty, gbc_btnPenalty);
		
		loanTablePanel = new LoanTablePanel();
		GridBagConstraints gbc_loanTablePanel = new GridBagConstraints();
		gbc_loanTablePanel.gridwidth = 6;
		gbc_loanTablePanel.fill = GridBagConstraints.BOTH;
		gbc_loanTablePanel.gridx = 0;
		gbc_loanTablePanel.gridy = 3;
		add(loanTablePanel, gbc_loanTablePanel);
	}

	public LoanDataPanel getLoanDataPanel() {
		return loanDataPanel;
	}

	public BookFilterPanel getBookFilterPanel() {
		return bookFilterPanel;
	}

	public VolumeTablePanel getVolumeTablePanel() {
		return volumeTablePanel;
	}

	public LoanTablePanel getLoanTablePanel() {
		return loanTablePanel;
	}

	public JButton getBtnLoan() {
		return btnLoan;
	}

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public JButton getBtnProlongation() {
		return btnProlongation;
	}

	public JButton getBtnPenalty() {
		return btnPenalty;
	}
}
