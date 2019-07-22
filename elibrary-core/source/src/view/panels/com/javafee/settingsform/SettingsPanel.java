package com.javafee.settingsform;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.javafee.common.BasePanel;
import com.javafee.common.Constants;
import com.javafee.common.SystemProperties;
import com.javafee.startform.LogInEvent;
import com.javafee.unicomponent.jtree.CustomJTree;

import lombok.Getter;

public class SettingsPanel extends BasePanel {
	@Getter
	private JTree treeMenu;
	@Getter
	private JPanel contentPanel;

	private InformationPanel informationPanel;
	@Getter
	private ThemePanel themePanel;
	@Getter
	private FontPanel fontPanel;
	@Getter
	private PersonalDataChangePanel personalDataChangePanel;
	@Getter
	private PasswordChangePanel passwordChangePanel;

	private GridBagConstraints gbc_panel;

	public SettingsPanel() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200, 350, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 5, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		treeMenu = new CustomJTree(constructTreeMenuNodes(), true);
		scrollPane.setViewportView(treeMenu);

		contentPanel = new JPanel();
		gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		add(contentPanel, gbc_panel);

		informationPanel = new InformationPanel();
		themePanel = new ThemePanel();
		fontPanel = new FontPanel();
		personalDataChangePanel = new PersonalDataChangePanel();
		passwordChangePanel = new PasswordChangePanel();
	}

	public void reloadContentPanel(SettingsForm settingsForm, JPanel contentPanel) {
		remove(this.contentPanel);

		this.contentPanel = contentPanel;
		gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		add(this.contentPanel, gbc_panel);

		settingsForm.getFrame().pack();
	}

	public JPanel reloadAndGetInformationPanel(String title, String description, String menuItems) {
		informationPanel.reloadLblTextContent(title, description, menuItems);
		return informationPanel;
	}

	private List<Object> constructTreeMenuNodes() {
		List<Object> nodes = new ArrayList<>();
		nodes.add(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuRoot"));

		List<Object> generalNodes = new ArrayList<>();
		generalNodes.add(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuGeneral"));
		generalNodes.add(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuTheme"));
		generalNodes.add(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuFont"));

		List<Object> accountNodes = new ArrayList<>();
		accountNodes.add(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuAccount"));
		accountNodes.add(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuDataChange"));
		accountNodes.add(SystemProperties.getInstance().getResourceBundle().getString("settingsPanel.treeMenuPasswordChange"));

		nodes.add(generalNodes);
		if (LogInEvent.getRole() != Constants.Role.ADMIN)
			nodes.add(accountNodes);

		return nodes;
	}
}
