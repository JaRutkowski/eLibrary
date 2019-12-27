package com.javafee.uniform;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import com.javafee.common.BasePanel;
import com.javafee.common.SystemProperties;
import com.javafee.unicomponent.border.CustomTitledBorder;
import com.javafee.unicomponent.jbutton.CustomJButton;
import com.javafee.unicomponent.jcombobox.CustomJComboBox;
import com.javafee.unicomponent.jlabel.CustomJLabel;

import lombok.Getter;

public class TemplateManagementPanel extends BasePanel {

	private static final long serialVersionUID = 6611305505467123577L;

	private JLabel lblChooseTemplate;
	@Getter
	private JComboBox<String> comboBoxLibraryTemplate;
	@Getter
	private JButton btnSaveTemplateToLibrary;
	@Getter
	private JButton btnPreviewTemplateLibrary;

	public TemplateManagementPanel() {
		super();
		setBorder(new CustomTitledBorder(null,
				SystemProperties.getInstance().getResourceBundle()
						.getString("htmlEditor.templateManagementBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, null));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{99, 393, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_panel);

		lblChooseTemplate = new CustomJLabel(
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.lblChooseTemplate"));
		GridBagConstraints gbc_lblChooseTemplate = new GridBagConstraints();
		gbc_lblChooseTemplate.fill = GridBagConstraints.VERTICAL;
		gbc_lblChooseTemplate.anchor = GridBagConstraints.EAST;
		gbc_lblChooseTemplate.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseTemplate.gridx = 0;
		gbc_lblChooseTemplate.gridy = 0;
		add(lblChooseTemplate, gbc_lblChooseTemplate);

		comboBoxLibraryTemplate = new CustomJComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		add(comboBoxLibraryTemplate, gbc_comboBox);

		btnSaveTemplateToLibrary = new CustomJButton(
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.btnSaveTemplateToLibrary"));
		GridBagConstraints gbc_btnNewButton11 = new GridBagConstraints();
		gbc_btnNewButton11.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton11.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton11.gridx = 1;
		gbc_btnNewButton11.gridy = 1;
		add(btnSaveTemplateToLibrary, gbc_btnNewButton11);

		btnPreviewTemplateLibrary = new CustomJButton(
				SystemProperties.getInstance().getResourceBundle().getString("htmlEditor.btnPreviewTemplateLibrary"));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 2;
		add(btnPreviewTemplateLibrary, gbc_btnNewButton_1);
	}

}
