package com.javafee.emailform.emails;

import java.awt.*;

import javax.swing.JPanel;

import com.javafee.common.BasePanel;
import com.javafee.common.Utils;
import com.javafee.uniform.HTMLeditorPanel;
import com.javafee.uniform.TemplateManagementPanel;

import lombok.Getter;

public class TemplatePagePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private HTMLeditorPanel htmlEditorPanel;
	@Getter
	private TemplateManagementPanel templateManagementPanel;

	public TemplatePagePanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{168, 771, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		htmlEditorPanel = new HTMLeditorPanel();
		htmlEditorPanel.getListStatus().setVisibleRowCount(10);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(5, 5, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(htmlEditorPanel, gbc_panel);

		templateManagementPanel = new TemplateManagementPanel();
		GridBagConstraints gbc_panel_templmanapan = new GridBagConstraints();
		gbc_panel_templmanapan.gridwidth = 2;
		gbc_panel_templmanapan.fill = GridBagConstraints.BOTH;
		gbc_panel_templmanapan.gridx = 0;
		gbc_panel_templmanapan.gridy = 1;
		add(templateManagementPanel, gbc_panel_templmanapan);
	}
}
