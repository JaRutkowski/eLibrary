package com.javafee.uniform;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.javafee.common.SystemProperties;

import lombok.Getter;

public class TemplateManagementPanel extends JPanel {

	private static final long serialVersionUID = 6611305505467123577L;

	private JLabel lblChooseTemplate;
	@Getter
	private JComboBox<String> comboBoxLibraryTemplate;
	@Getter
	private JButton btnSaveTemplateToLibrary;
	@Getter
	private JButton btnPreviewTemplateLibrary;

	public TemplateManagementPanel() {
		setBorder(new TitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("htmlEditor.templateManagementBorderTitle"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 99, 393, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gbl_panel);

		lblChooseTemplate = new JLabel(
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.lblChooseTemplate"));
		GridBagConstraints gbc_lblWybierzSzablonZ = new GridBagConstraints();
		gbc_lblWybierzSzablonZ.fill = GridBagConstraints.VERTICAL;
		gbc_lblWybierzSzablonZ.anchor = GridBagConstraints.EAST;
		gbc_lblWybierzSzablonZ.insets = new Insets(0, 0, 5, 5);
		gbc_lblWybierzSzablonZ.gridx = 0;
		gbc_lblWybierzSzablonZ.gridy = 0;
		add(lblChooseTemplate, gbc_lblWybierzSzablonZ);

		comboBoxLibraryTemplate = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		add(comboBoxLibraryTemplate, gbc_comboBox);

		btnSaveTemplateToLibrary = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.btnSaveTemplateToLibrary"));
		GridBagConstraints gbc_btnNewButton11 = new GridBagConstraints();
		gbc_btnNewButton11.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton11.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton11.gridx = 1;
		gbc_btnNewButton11.gridy = 1;
		add(btnSaveTemplateToLibrary, gbc_btnNewButton11);

		btnPreviewTemplateLibrary = new JButton(
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.btnPreviewTemplateLibrary"));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 2;
		add(btnPreviewTemplateLibrary, gbc_btnNewButton_1);
	}

}
