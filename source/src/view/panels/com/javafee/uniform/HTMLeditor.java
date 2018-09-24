package com.javafee.uniform;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JEditorPane;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

import lombok.Getter;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;

public class HTMLeditor extends JPanel {

	private static final long serialVersionUID = -7643308432142313985L;

	@Getter
	private JEditorPane htmlEditorPane;
	@Getter
	private JTextArea previewTextArea;
	@Getter
	private JLabel lblStatus;
	
	public HTMLeditor() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{338, 0, 372, 0};
		gridBagLayout.rowHeights = new int[]{282, 253, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel htmlEditorPanel = new JPanel();
		htmlEditorPanel.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_htmlEditorPanel = new GridBagConstraints();
		gbc_htmlEditorPanel.gridheight = 2;
		gbc_htmlEditorPanel.insets = new Insets(0, 0, 5, 5);
		gbc_htmlEditorPanel.fill = GridBagConstraints.BOTH;
		gbc_htmlEditorPanel.gridx = 0;
		gbc_htmlEditorPanel.gridy = 0;
		add(htmlEditorPanel, gbc_htmlEditorPanel);
		GridBagLayout gbl_htmlEditorPanel = new GridBagLayout();
		gbl_htmlEditorPanel.columnWidths = new int[]{189, 0};
		gbl_htmlEditorPanel.rowHeights = new int[]{0, 0};
		gbl_htmlEditorPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_htmlEditorPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		htmlEditorPanel.setLayout(gbl_htmlEditorPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		htmlEditorPanel.add(scrollPane, gbc_scrollPane);
		
		htmlEditorPane = new JEditorPane();
		htmlEditorPane.setContentType("text/html");
		htmlEditorPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setViewportView(htmlEditorPane);
		
		JButton btnNewButton = new JButton(">>");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.SOUTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		add(btnNewButton, gbc_btnNewButton);
		
		JPanel previewPanel = new JPanel();
		previewPanel.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_previewPanel = new GridBagConstraints();
		gbc_previewPanel.gridheight = 2;
		gbc_previewPanel.insets = new Insets(0, 0, 5, 0);
		gbc_previewPanel.fill = GridBagConstraints.BOTH;
		gbc_previewPanel.gridx = 2;
		gbc_previewPanel.gridy = 0;
		add(previewPanel, gbc_previewPanel);
		GridBagLayout gbl_previewPanel = new GridBagLayout();
		gbl_previewPanel.columnWidths = new int[]{228, 0};
		gbl_previewPanel.rowHeights = new int[]{0, 0};
		gbl_previewPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_previewPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		previewPanel.setLayout(gbl_previewPanel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		previewPanel.add(scrollPane_1, gbc_scrollPane_1);
		
		previewTextArea = new JTextArea();
		previewTextArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane_1.setViewportView(previewTextArea);
		
		JButton button = new JButton("?");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.NORTH;
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 1;
		add(button, gbc_button);
		
		lblStatus = new JLabel("Status");
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.WEST;
		gbc_lblStatus.gridwidth = 3;
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 2;
		add(lblStatus, gbc_lblStatus);

	}

}
